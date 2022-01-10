package com.company;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {
public static final GsonBuilder BUILDER=new  GsonBuilder();
public static final Gson GSON=BUILDER.setPrettyPrinting().create();

    public static void main(String[] args) throws IOException {
         Scanner scanner = new Scanner(System.in);
        String data = new String(Files.readAllBytes(Paths.get("info.json")));
        String data2 = new String(Files.readAllBytes(Paths.get("info2.json")));
        JSONArray jsonArray = new JSONArray(data);
        JSONArray jsonArray2 = new JSONArray(data2);
        AutoBase[] autoBases ={ new AutoBase(1,"Volvo","Nurik","On Base"),
                new AutoBase(2,"Volvo","Bektur","On Base"),
                new AutoBase(3,"Volvo","Janyl","On Base")};
        Drivers[] drivers = new Drivers[jsonArray2.length()];
        int a;
        int b;
        for (int i = 0; i < autoBases.length; i++) {
            autoBases[i] = new AutoBase();
            String str = jsonArray.get(i).toString();
            JSONObject object = new JSONObject(str);

            autoBases[i].setId(object.getInt("id"));
            autoBases[i].setName(object.getString("name"));
            autoBases[i].setDriver(object.getString("driver"));
            autoBases[i].setState(object.getString("state"));
            object.put("driver", drivers[i]);
        }

        System.out.println("#   |   BUS          |  DRIVER|  STATE ");
        System.out.println("----+----------------+--------+--------");

        for (int i = 0; i < 3; i++) {
            System.out.printf("%-3s|", autoBases[i].getId());
            System.out.printf("%-17s|", autoBases[i].getName());
            System.out.printf("%-8s|", autoBases[i].getDriver());
            System.out.printf("%-10s|", autoBases[i].getState());
            System.out.println();

        }
        System.out.println("========================================================");

        System.out.print("Choose one of the truck:  ");
       a = scanner.nextByte();
        a--;
        while (true) {
            System.out.println("----------------- TRUCK-INFO ---------------------------");
            System.out.println(
                    "id        :      " + autoBases[a].getId() +
                            "\nBus       :      " + autoBases[a].getName() +
                            "\nDriver    :      " +
                            "\nBus State :      " + autoBases[a].getState());
            System.out.println("Press 1 change to driver");
            System.out.println("Press 2 to send to the Route");
            System.out.println("Press 3 to send to the Repairing");

            break;
        }



            for (int i = 0; i < drivers.length; i++) {
                drivers[i] = new Drivers();
                String str2 = jsonArray2.get(i).toString();
                JSONObject object = new JSONObject(str2);

                drivers[i].setId(object.getString("id"));
                drivers[i].setName(object.getString("name"));
                drivers[i].setBus(object.getString("bus"));
            }



         b = scanner.nextByte();
        if (b == 1) {
            System.out.println("Driver changed successfully");
            b--;
            System.out.println("|---------------------DRIVERS-----------------|");
            System.out.println("|# | id        |        Driver| Bus");
            System.out.println("|--|-----------|--------------|----|");
            for (int i = 0; i < 3; i++) {
                System.out.printf("|%-2s",i + 1);
                System.out.printf("|%-11s|",drivers[i].getId() );
                System.out.printf("|%-13s",drivers[i].getName());
                if(drivers[i].getName()==drivers[a].getName()) {
                    System.out.printf("|%-3s", autoBases[a].getName());
                    System.out.println();
                }else {
                    System.out.println(" ");
                }
            }
        }
//        else if (b == 2) {
//            System.out.println("Driver changed successfully");
//            System.out.println("|---------------------DRIVERS-----------------|");
//            System.out.println("|# | id        |        Driver| Bus");
//            System.out.println("|--|-----------|--------------|----|");
//        System.out.println(autoBases[0]);
//        System.out.println(autoBases[1]);
//        System.out.println(autoBases[2]);
//        }
        String json =GSON.toJson(autoBases);
        write(json);
    }
    private static void write(String obj)throws IOException{
        Path wraiter=Paths.get("./autopark.json");
        try{
            Files.writeString(wraiter,obj, StandardOpenOption.CREATE,StandardOpenOption.WRITE);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
