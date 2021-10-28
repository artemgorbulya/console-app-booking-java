package booking.Utils;

import booking.ConstEnum.DataUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeStamp implements DataUtil {

  public static long seconds (LocalDateTime time) {
    return time.toEpochSecond(ZoneId.of(TIME_ZONE).getRules().getOffset(time));
  }
}
