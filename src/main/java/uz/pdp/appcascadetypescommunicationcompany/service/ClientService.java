package uz.pdp.appcascadetypescommunicationcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.appcascadetypescommunicationcompany.CommonUtils;
import uz.pdp.appcascadetypescommunicationcompany.entity.ClientMoveType;
import uz.pdp.appcascadetypescommunicationcompany.entity.Detail;
import uz.pdp.appcascadetypescommunicationcompany.entity.Simcard;
import uz.pdp.appcascadetypescommunicationcompany.entity.SimcardSet;
import uz.pdp.appcascadetypescommunicationcompany.enums.ClientMoveTypeEnum;
import uz.pdp.appcascadetypescommunicationcompany.payload.ApiResponse;
import uz.pdp.appcascadetypescommunicationcompany.repository.*;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SimcardRepository simcardRepository;

    @Autowired
    DetailRepository detailRepository;

    @Autowired
    SimcardSetRepository simcardSetRepository;

    @Autowired
    ClientMoveTypeRepository clientMoveTypeRepository;

    public ApiResponse checkBalance() {
        Simcard principalSimCard = (Simcard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ApiResponse("Balance", true, principalSimCard.getBalance());
    }





    @Transactional
    public ApiResponse sendMessage(Integer numberOfSms) {
        Simcard principalSimCard = (Simcard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID simCardId = principalSimCard.getId();

        Optional<Simcard> optionalSimcard = simcardRepository.findById(simCardId);
        Simcard simcard = optionalSimcard.get();
        SimcardSet simcardSet = simcard.getSimcardSet();

        Integer oldNumberOfSms = simcardSet.getSms();
        Integer amount = oldNumberOfSms - numberOfSms;
        Double amountBalance = 0.0;
        Double smsPrice = simcard.getTariff().getTariffSet().getSmsPrice();
        if (amount < 0) {
            amountBalance = smsPrice * Math.abs(amount);
            simcard.setBalance(simcard.getBalance() - amountBalance);
            simcardRepository.save(simcard);
            simcardSet.setSms(0);
        } else {
            simcardSet.setSms(amount);
        }

        simcardSetRepository.save(simcardSet);

        String description = "Foydalanuvchi xabar yubordi" + (amount >= 0
                ? (numberOfSms + " SMS chegarasida")
                : (oldNumberOfSms + " SMS chegarasida va " + amountBalance + " so'mi balansdan chegirib tashlandi " + Math.abs(amount) + " SMS (" + smsPrice + " 1 SMS uchun so'm)"));

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeEnum.SENT_MESSAGE);
        Detail detail = CommonUtils.createDetail("Foydalanuvchi xabar yubordi", description, clientMoveType, simcard);
        detailRepository.save(detail);

        return new ApiResponse(description, true);
    }




    @Transactional
    public ApiResponse call(Integer minutes) {
        Simcard principalSimCard = (Simcard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID simCardId = principalSimCard.getId();

        Optional<Simcard> optionalSimcard = simcardRepository.findById(simCardId);
        Simcard simcard = optionalSimcard.get();
        SimcardSet simcardSet = simcard.getSimcardSet();
        Integer oldMinutes = simcardSet.getMinute();
        int newMinutes = oldMinutes - minutes;
        Double amountBalance = 0.0;
        Double outgoingCallPrice = simcard.getTariff().getTariffSet().getOutgoingCallPrice();
        if (newMinutes < 0) {
            amountBalance = outgoingCallPrice * Math.abs(newMinutes);
            simcard.setBalance(simcard.getBalance() - amountBalance);
            simcardRepository.save(simcard);
            simcardSet.setMinute(0);

        } else {
            simcardSet.setMinute(newMinutes);
        }
        simcardSetRepository.save(simcardSet);
        String description = "Foydalanuvchi chaqirdi " + (newMinutes >= 0
                ? (minutes + " daqiqa chegarasi")
                : (oldMinutes + " daqiqa chegarasi va " + amountBalance + " so‘mi balansdan chegirib tashlandi " + Math.abs(newMinutes) + " minut (" + outgoingCallPrice + " 1 Minute uchun so'm)"));

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeEnum.CALLED);
        Detail detail = CommonUtils.createDetail("Foydalanuvchi qo'ng'iroq qildi", description, clientMoveType, simcard);
        detailRepository.save(detail);

        return new ApiResponse(description, true);

    }




    @Transactional
    public ApiResponse useInternet(Double amountOfMb) {
        Simcard principalSimCard = (Simcard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID simCardId = principalSimCard.getId();

        Optional<Simcard> optionalSimcard = simcardRepository.findById(simCardId);
        Simcard simcard = optionalSimcard.get();
        SimcardSet simcardSet = simcard.getSimcardSet();
        Double oldMb = simcardSet.getMb();
        Double newAmountMb = oldMb - amountOfMb;
        Double amountBalance = 0.0;
        Double trafficPrice = simcard.getTariff().getTariffSet().getInternetTrafficPrice();
        if (newAmountMb < 0) {
            amountBalance = trafficPrice * Math.abs(newAmountMb);
            simcard.setBalance(simcard.getBalance() - amountBalance);
            simcardRepository.save(simcard);
            simcardSet.setMb(0.0);
        } else {
            simcardSet.setMb(newAmountMb);
        }
        simcardSetRepository.save(simcardSet);
        String description = "Foydalanuvchi ishlatgan " + (newAmountMb >= 0
                ? (amountOfMb + " MB chegarasida")
                : (oldMb + " MB chegarasida " + amountBalance + " so'mi balansdan chegirib tashlandi " + Math.abs(newAmountMb) + " MB (" + trafficPrice + " 1 MB so'm uchun)"));

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeEnum.USED_THE_INTERNET);
        Detail detail = CommonUtils.createDetail("Foydalanuvchi internetdan foydalangan", description, clientMoveType, simcard);
        detailRepository.save(detail);

        return new ApiResponse(description, true);
    }
}
