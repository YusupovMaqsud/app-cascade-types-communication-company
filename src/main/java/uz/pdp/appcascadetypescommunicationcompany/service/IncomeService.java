package uz.pdp.appcascadetypescommunicationcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.appcascadetypescommunicationcompany.entity.Income;
import uz.pdp.appcascadetypescommunicationcompany.entity.User;
import uz.pdp.appcascadetypescommunicationcompany.enums.RoleEnum;
import uz.pdp.appcascadetypescommunicationcompany.payload.ApiResponse;
import uz.pdp.appcascadetypescommunicationcompany.repository.IncomeRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class IncomeService {
    @Autowired
    IncomeRepository incomeRepository;

    public ApiResponse getIncomesByDaily() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (user.getRoles().equals(RoleEnum.ROLE_DIRECTOR) || user.getRoles().equals(RoleEnum.ROLE_BRANCH_DIRECTOR) || user.getRoles().equals(RoleEnum.ROLE_FILIAL_MANAGER)) {
            return new ApiResponse("Sizda vakolat yo'q", false);
        }

        LocalDate localDate = LocalDate.now();
        LocalDateTime startOfDay = localDate.atTime(LocalTime.MIN);
        LocalDateTime endOfDate = LocalTime.MAX.atDate(localDate);
        Timestamp minDate = Timestamp.valueOf(startOfDay);
        Timestamp maxDate = Timestamp.valueOf(endOfDate);
        List<Income> incomes = incomeRepository.findAllByDateBetween(minDate, maxDate);
        return new ApiResponse("Kundalik daromadlar", true, incomes);
    }

    public ApiResponse getIncomesByMonthly() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (user.getRoles().equals(RoleEnum.ROLE_DIRECTOR) || user.getRoles().equals(RoleEnum.ROLE_BRANCH_DIRECTOR) || user.getRoles().equals(RoleEnum.ROLE_FILIAL_MANAGER)) {
            return new ApiResponse("Sizda vakolat yo'q", false);
        }


        java.util.Date begining;
        java.util.Date end;

        Calendar calendar1 = getCalendarForNow();
        calendar1.set(Calendar.DAY_OF_MONTH,
                calendar1.getActualMinimum(Calendar.DAY_OF_MONTH));
        setTimeToBeginningOfDay(calendar1);
        begining = calendar1.getTime();

        Calendar calendar2 = getCalendarForNow();
        calendar2.set(Calendar.DAY_OF_MONTH,
                calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
        setTimeToEndOfDay(calendar2);
        end = calendar2.getTime();

        List<Income> incomes = incomeRepository.findAllByDateBetween(new Timestamp(begining.getTime()), new Timestamp(end.getTime()));

        return new ApiResponse("Oylik daromadlar", true, incomes);
    }



    private static Calendar getCalendarForNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }



    private static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }


    private static void setTimeToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
}
