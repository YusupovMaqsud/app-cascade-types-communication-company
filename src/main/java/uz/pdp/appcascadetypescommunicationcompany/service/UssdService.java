package uz.pdp.appcascadetypescommunicationcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.appcascadetypescommunicationcompany.CommonUtils;
import uz.pdp.appcascadetypescommunicationcompany.entity.*;
import uz.pdp.appcascadetypescommunicationcompany.enums.ClientMoveTypeEnum;
import uz.pdp.appcascadetypescommunicationcompany.enums.RoleEnum;
import uz.pdp.appcascadetypescommunicationcompany.payload.ApiResponse;
import uz.pdp.appcascadetypescommunicationcompany.repository.*;
import java.util.List;
import java.util.UUID;

@Service
public class UssdService {
    @Autowired
    UssdRepository ussdRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SimcardRepository simcardRepository;
    @Autowired
    SimcardSetRepository simcardSetRepository;
    @Autowired
    TariffRepository tariffRepository;
    @Autowired
    ExtraServiceRepository extraServiceRepository;
    @Autowired
    DetailRepository detailRepository;
    @Autowired
    ClientMoveTypeRepository clientMoveTypeRepository;


    public List<Ussd> getAllUssdCodes() {
        List<Ussd> ussdList = ussdRepository.findAll();
        return ussdList;
    }




    public Tariff getTariffByUserId(UUID userId) {
        Simcard simcard = simcardRepository.getByClientId(userId);
        Tariff tariff = simcard.getTariff();
        return tariff;
    }




    @Transactional
    public ApiResponse changeTariff(Integer tariffId, UUID simcardId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (user.getRoles().equals(RoleEnum.ROLE_WORKER) || user.getRoles().equals(RoleEnum.ROLE_CLIENT)) {
            return new ApiResponse("Sizda vakolat yo'q", false);
        }


        Tariff tariff = tariffRepository.findById(tariffId).get();
        Double switchCostAmount = tariff.getSwitchCost();

        Simcard simcard = simcardRepository.findById(simcardId).get();

        if (simcard.getBalance() < switchCostAmount) {
            return new ApiResponse("Tarifga o'tish uchun pulingiz yetarli emas", false);
        }

        simcard.setBalance(simcard.getBalance() - switchCostAmount);
        simcard.setTariff(tariff);
        TariffSet tariffSet = tariff.getTariffSet();
        SimcardSet simcardSet = simcard.getSimcardSet();
        simcardSet.setMb(tariffSet.getMb());
        simcardSet.setSms(tariffSet.getSms());
        simcardSet.setMinute(tariffSet.getMinute());
        simcardSetRepository.save(simcardSet);

        simcardRepository.save(simcard);

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeEnum.CHANGED_TARIFF);
        Detail detail = CommonUtils.createDetail("Foydalanuvchi tarifni o'zgartirdi", "", clientMoveType, simcard);
        detailRepository.save(detail);

        return new ApiResponse("Tarifga muvaffaqiyatli o'tildi", true);
    }





    @Transactional
    public ApiResponse buyExtraService(Integer serviceId, UUID simcardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (user.getRoles().equals(RoleEnum.ROLE_WORKER) || user.getRoles().equals(RoleEnum.ROLE_CLIENT)) {
            return new ApiResponse("Sizda vakolat yo'q", false);
        }


        ExtraService extraService = extraServiceRepository.getById(serviceId);
        Simcard simcard = simcardRepository.getById(simcardId);

        Double servicePrice = extraService.getPrice();
        if (simcard.getBalance() < servicePrice) {
            return new ApiResponse("Bunday xizmatni sotib olish uchun pulingiz yetarli emas", false);
        }

        List<ExtraService> serviceList = simcard.getCurrentService();

        serviceList.add(extraService);
        simcard.setCurrentService(serviceList);
        simcard.setBalance(simcard.getBalance() - servicePrice);
        simcardRepository.save(simcard);

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeEnum.PURCHASED_EXTRA_SERVICE);
        Detail detail = CommonUtils.createDetail("Foydalanuvchi qo'shimcha xizmatni sotib oldi", "", clientMoveType, simcard);
        detailRepository.save(detail);

        return new ApiResponse("Xizmat muvaffaqiyatli sotib olindi", true);
    }


}
