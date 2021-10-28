package booking.Utils;

import booking.ConstEnum.DataUtil;
import booking.ConstEnum.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Logger implements FileUtil, DataUtil {

    public Logger (){
        File dir = new File(LOG_FILE_PATH); //для перегенерации коллекции удалить файл ./src/file/log.txt

        if (dir.isFile()) {
            System.out.println("Файл логирования найден...");

        } else {
            System.out.println("Файл логирования не найден...");
            try
            {
                boolean created = dir.createNewFile();
                if(created)
                    System.out.println("Файл логирования был создан");
            }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }
        }
    }

    public void addLogString(String str){
        try(FileWriter writer = new FileWriter(LOG_FILE_PATH, true))
        {

            long epoch = System.currentTimeMillis()/1000;
            String timestr = Instant.ofEpochSecond(epoch)
                    .atZone(ZoneId.of(TIME_ZONE))
                    .toLocalDateTime()
                    .format(DateTimeFormatter
                            .ofPattern(DATE_TIME_FORMAT)).toString();

            String res = timestr + " - " + str;

            writer.append(res);
            writer.append('\n');

        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

}
