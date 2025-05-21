package com.mli.mpro.location.services;

import java.util.HashMap;

public interface PincodeMasterService {

	 HashMap<String, String> getStatesAndCitiesByPincode(String pincode);
}
