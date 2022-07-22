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
import uz.pdp.appcascadetypescommunicationcompany.payload.SimcardDto;
import uz.pdp.appcascadetypescommunicationcompany.repository.*;

@Service
public class FilialService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SimcardRepository simcardRepository;
    @Autowired
    SimcardSetRepository simcardSetRepository;
    @Autowired
    TariffRepository tariffRepository;
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    ClientMoveTypeRepository clientMoveTypeRepository;
    @Autowired
    PaymentTypeRepository paymentTypeRepository;
    @Autowired
    RoleRepository roleRepository;


    @Transactional
    public ApiResponse buySimcard(SimcardDto simcardDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (user.getRoles().equals(RoleEnum.ROLE_WORKER)) {
            return new ApiResponse("Sizda vakolat yo'q", false);
        }

        Simcard simcard = new Simcard();
        simcard.setCompanyCode(simcardDto.getCompanyCode());
        simcard.setNumber(simcardDto.getNumber());
        simcard.setBalance(simcardDto.getBalance());
        simcard.setStatus(simcardDto.isStatus());


        User client = userRepository.findById(simcardDto.getClientId()).get();
        simcard.setClient(client);

        Tariff tariff = tariffRepository.findById(simcardDto.getTariffId()).get();
        simcard.setTariff(tariff);

        TariffSet tariffSet = tariff.getTariffSet();
        SimcardSet simcardSet = new SimcardSet();
        simcardSet.setMb(tariffSet.getMb());
        simcardSet.setSms(tariffSet.getSms());
        simcardSet.setMinute(tariffSet.getMinute());
        SimcardSet savedSimcardSet = simcardSetRepository.save(simcardSet);
        simcard.setSimcardSet(savedSimcardSet);

        Simcard savedSimcard = simcardRepository.save(simcard);

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeEnum.PURCHASED_SIMCARD);
        PaymentType paymentType = paymentTypeRepository.findById(simcardDto.getPaymentTypeId()).get();
        Income income = CommonUtils.createIncome(simcardDto.getPrice(), savedSimcard, clientMoveType, paymentType);
        incomeRepository.save(income);

        return new ApiResponse("Simkarta muvaffaqiyatli sotib olindi", true, savedSimcard);
    }

}
