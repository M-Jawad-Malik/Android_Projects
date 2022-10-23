package com.example.resortingapp;

import java.util.ArrayList;
import java.util.List;

public class RegionsList {
	public static List<String> getList(String shortState){

		List<String> regions = new ArrayList<>();

		regions.add("All Regions");

		switch (shortState) {
			case "PP":
				regions.add("Bahawalnagar");
				regions.add("Bahawalpur");
				regions.add("Bhakkar");
				regions.add("Chakwal");
				regions.add("Chiniot");
				regions.add("Dera Ghazi Khan");
				regions.add("Faisalabad");
				regions.add("Gujranwala");
				regions.add("Gujrat");
				regions.add("Hafizabad");
				regions.add("Islamabad");
				regions.add("Jhang");
				regions.add("Jhelum");
				regions.add("Kasur");
				regions.add("Khanewal");
				regions.add("Jauharabad");
				regions.add("Lahore");
				regions.add("Layyah");
				regions.add("Lodhran");
				regions.add("Mandi Bahauddin	");
				regions.add("Mianwali");
				regions.add("Multan");
				regions.add("Muzaffargarh");
				regions.add("Nankana Sahib");
				regions.add("Narowal");
				regions.add("Okara");
				regions.add("Pakpattan");
				regions.add("Rahim Yar Khan");
				regions.add("Rajanpur");
				regions.add("Rawalpindi");
				regions.add("Sahiwal");
				regions.add("Sargodha");
				regions.add("Sheikhupura");
				regions.add("Sialkot");
				regions.add("Toba Tek Singh");
				regions.add("Vehari");

				break;
			case "KP":
				regions.add("Peshawar");
				regions.add("Mardan");
				regions.add("Mingora");
				regions.add("Kohat");
				regions.add("Dera Ismail Khan");
				regions.add("Abbottabad");
				regions.add("Mansehra");
				regions.add("Swabi");
				regions.add("Nowshera");
				regions.add("Kabal");
				regions.add("Charsadda");
				regions.add("Barikot");
				regions.add("Shabqadar");
				regions.add("Haripur");
				regions.add("Takht-i-Bahi");
				regions.add("Paharpur");
				regions.add("Batkhela");
				regions.add("Jamrud");
				regions.add("Bahrain");
				regions.add("Lakki Marwat");
				regions.add("1Pabbi");
				regions.add("Topi");
				regions.add("Jehangira");
				regions.add("Karak");
				regions.add("Bannu");
				regions.add("Chitral");
				regions.add("Hangu");
				regions.add("Havelian");
				regions.add("Khwazakhela");
				regions.add("Khalabat");
				regions.add("Tank");
				regions.add("Dir");
				regions.add("Matta");
				regions.add("Tordher");
				regions.add("Timargara");

				break;
			case "BL":
				regions.add("Quetta");
				regions.add("Turbat");
				regions.add("Khuzdar");
				regions.add("Hub");
				regions.add("Chaman");
				regions.add("Dera Murad Jamali");
				regions.add("Gwadar");
				regions.add("Dera Allah Yar");
				regions.add("Usta Mohammad");
				regions.add("Sui Town");
				regions.add("Sibi");
				regions.add("Loralai");
				regions.add("Tump");
				regions.add("Nushki");
				regions.add("Zhob");
				regions.add("Kharan");
				regions.add("Chitkan");
				regions.add("Khanozai");
				regions.add("Buleda");
				regions.add("Saranan");
				regions.add("Zehri");
				regions.add("Qalat");
				regions.add("Tasp");
				regions.add("Surab");
				regions.add("Pishin");
				regions.add("Mastung");
				regions.add("Qilla Saifullah");
				regions.add("Pasni");
				regions.add("Nal");
				regions.add("Winder");
				regions.add("Uthal");
				regions.add("Huramzai");
				regions.add("Muslim Bagh");
				regions.add("Dera Bugti");
				break;
			case "SH":
				regions.add("karachi");
				regions.add("Bhiria");
				regions.add("Bhiria Road");
				regions.add("Bhirkan");
				regions.add("Boriri");
				regions.add("Chak");
				regions.add("Dadu");
				regions.add("Daharki");
				regions.add("Daulatpur");
				regions.add("Digri");
				regions.add("Diplo");
				regions.add("Dokri");
				regions.add("Gambat");
				regions.add("Garhi Yasin");
				regions.add("Ghotki");
				regions.add("Guddu Barrage");
				regions.add("Hala");
				regions.add("Khairpur Mirs");
				regions.add("Hyderabad");
				regions.add("Islamkot");
				regions.add("Jacobabad");
				regions.add("Jamshoro");
				regions.add("Jam sahib");
				regions.add("Kandhkot");
				regions.add("Kandiaro");
				regions.add("Karachi");
				regions.add("Kashmore");
				regions.add("Keti Bandar");
				regions.add("Khadro");
				regions.add("Khairpur");
				regions.add("Khipro");
				regions.add("Kiamari");
				regions.add("Korangi");
				regions.add("Kot Diji");
				regions.add("Qilla Abdullah");
				break;
		}

		return regions;

	}
}
