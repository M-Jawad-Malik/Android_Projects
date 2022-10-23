package com.example.animalkingdom.helper;


import com.example.animalkingdom.model.Province;

import java.util.ArrayList;
import java.util.List;

public class ProvinceList {

   public static List<Province> getList(){

       List<Province> provinceList = new ArrayList<>();
       provinceList.add(new Province("Pakistan", ""));
       provinceList.add(new Province("Punjab", "PP"));
       provinceList.add(new Province("Khayber Pakhtunkhwa", "KP"));
       provinceList.add(new Province("Balochistan", "BL"));
       provinceList.add(new Province("Sindh", "SH"));


       return provinceList;
   }

}
