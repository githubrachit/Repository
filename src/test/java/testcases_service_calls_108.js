/***
TestCase Rule-For  Msg: "Pan Number |  {Pan_Number}"		
						IF “Nationality= Indian” AND “panNumber not exist” THEN Msg

*/
function panValidationAllowCase(output) {
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "",
						"insurerEducation": "",
						"income": "",
						"insurerAnnualIncome": "",
						"occupation": "",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	};

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}

	return restrictionRequestPayload;
}
/***
TestCase Rule-For  Msg: "Pan Number |  {Pan_Number}"		
						IF “Nationality= Indian” AND “panNumber not exist” THEN Msg

*/
function panValidationRejectCase(output) {
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "",
						"insurerEducation": "",
						"income": "",
						"insurerAnnualIncome": "",
						"occupation": "",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize  == 1 && output.response.responseData.responsePayload.messages[0] == "Pan number | null";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/***
TestCase Rule-For :: For Msg: "Proposer education |  {education_value}"

						IF “Occupation(proposer)=HouseWife”  
						AND “Education(proposer) in GraduateAndAboveGraduate”  
						AND “AnnualIncome(proposer) <= MinimumEducationIncome based on channel(from DB)” THEN  Msg
*/

function educationHouseWifeWithIncomeRejectCase(output) {  //occupation-Housewife,education-Senior School(NOT IN GraduateAndAboveGraduate), income<=1000000000(10^9), THEN Msg: Proposer education | SENIOR SCHOOL
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "Senior School",
						"insurerEducation": "",
						"income": "100000000",
						"insurerAnnualIncome": "",
						"occupation": "Housewife",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Proposer education | SENIOR SCHOOL";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/***
TestCase Rule-For :: For Msg: "Proposer education |  {education_value}"

						IF “Occupation(proposer)=HouseWife”  
						AND “Education(proposer) in GraduateAndAboveGraduate”  
						AND “AnnualIncome(proposer) <= MinimumEducationIncome based on channel(from DB)” THEN  Msg
*/
function educationHouseWifeWithIncomeAllowCase(output) {  // education-GRADUATE //occupation-Housewife,education-SENIOR SCHOOL(NOT IN GraduateAndAboveGraduate), income<=1000000000(10^9), THEN Msg: Proposer education | SENIOR SCHOOL
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "GRADUATE",
						"insurerEducation": "",
						"income": "100000000",
						"insurerAnnualIncome": "",
						"occupation": "Housewife",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/***
TestCase Rule-For :: For Msg: "Proposer education |  {education_value}"
		IF Education(proposer) IN (“underGraduateEducations – High School”) AND “AnnualIncome(proposer) <= MinimumEducationIncome based on channel(from DB)” THEN  Msg
*/

function educationWithIncomeRejectCase(output) { //education-DIPLOMA, income<=1000000000, THEN Msg: Proposer education | DIPLOMA
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "DIPLOMA",
						"insurerEducation": "",
						"income": "1000000000", // If 1000000005 THEN NO-MSG
						"insurerAnnualIncome": "",
						"occupation": "",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Proposer education | DIPLOMA";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/***
TestCase Rule-For :: For Msg: "Proposer education |  {education_value}"
		IF Education(proposer) IN (“underGraduateEducations – High School”) AND “AnnualIncome(proposer) <= MinimumEducationIncome based on channel(from DB)” THEN  Msg
*/


function educationWithIncomeAllowCase(output) { //income=1000000005, education-DIPLOMA  // No Msg Because, 1000000005 IN NOT LESS OR EQUAL TO 1000000000
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "DIPLOMA",
						"insurerEducation": "",
						"income": "1000000005", 
						"insurerAnnualIncome": "",
						"occupation": "",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/***
TestCase Rule-For :: For Msg: "Proposer education |  {education_value}"
		IF Education(proposer) IN (“underGraduateEducations – High School”) AND “AnnualIncome(proposer) <= MinimumEducationIncome based on channel(from DB)” THEN  Msg
*/

function educationHighSchoolWithIncomeAllowCase(output) { // education-High School, income-100000000(Not mandatory) 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "High School",
						"insurerEducation": "",
						"income":"100000000",
						"insurerAnnualIncome": "",
						"occupation": "",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”							Channel K means SPARC
						IF Channel IN(K, CAT) AND 					
						Case 1:	IF Occupation(proposer) =“Salaried” AND AnnualIncome(proposer) < SparcSelfIncome(from DB) AND age(proposer)<=SparcAgeLimit(from DB)-300000 THEN Msg		
						Case 2:	IF Occupation(proposer)=“Self Employed” AND AnnualIncome(proposer) < SparcSelfEmployedIncome(from DB) AND age(proposer)<=SparcAgeLimit(from DB) THEN Msg		

*/
function channelSparc_OccupationSalaried_SparcIncomeAge_RejectCase(output) {  //channel=K,income-290000,occupation-Salaried,dateOfBirth-"2003-02-28T18:30:00.000+0000" / Message: Proposer annual income | 290000
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "High School",
						"insurerEducation": "",
						"income": "290000",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Proposer annual income | 290000";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”							Channel K means SPARC
						IF Channel IN(K, CAT) AND 					
						Case 1:	IF Occupation(proposer) =“Salaried” AND AnnualIncome(proposer) < SparcSelfIncome(from DB) AND age(proposer)<=SparcAgeLimit(from DB)-300000 THEN Msg		
						Case 2:	IF Occupation(proposer)=“Self Employed” AND AnnualIncome(proposer) < SparcSelfEmployedIncome(from DB) AND age(proposer)<=SparcAgeLimit(from DB) THEN Msg		
	// Also allow to pass Case 4.a for NO message
*/
function channelSparc_OccupationSalaried_SparcIncomeAgeAllowCase(output) {  //channel=K,income-500000,occupation-Salaried,dateOfBirth-"2003-02-28T18:30:00.000+0000" /No Message:
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "Graduate",
						"insurerEducation": "",
						"income": "500000",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”							Channel K means SPARC
						IF Channel IN(K, CAT) AND 					
						Case 3:	IF AnnualIncome(proposer) < MinimumIncome(from DB) AND age(proposer)>SparcAgeLimit(from DB) THEN Msg
*/

function channelSparc_AboveAge_RejectCase(output) {  //channel=K,income-450000 /Message://Proposer annual income | 450000
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "Graduate",
						"insurerEducation": "",
						"income": "450000",
						"insurerAnnualIncome": "",
						"occupation": "PROFESSIONAL",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "1980-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Proposer annual income | 450000";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”							Channel K means SPARC
						IF Channel IN(K, CAT) AND 					
						Case 3:	IF AnnualIncome(proposer) < MinimumIncome(from DB) AND age(proposer)>SparcAgeLimit(from DB) THEN Msg
*/


function channelSparc_AboveAge_AllowCase(output) {  //channel=K,income-500000 /NO Message:
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "Graduate",
						"insurerEducation": "",
						"income": "500000",
						"insurerAnnualIncome": "",
						"occupation": "PROFESSIONAL",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "1980-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}



/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”							Channel K means SPARC
						IF Channel IN(K, CAT) AND 					
						Case 4.a:	IF AnnualIncome(proposer) < MinimumIncome(from DB)-500000 AND Education(proposer) NOT “underGraduateEducations” THEN Msg

*/

function channelSparc_EducationNotUnderGraduate_RejectCase(output) {  //channel=K,income-450000 /Message://Proposer annual income | 450000
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "Graduate",
						"insurerEducation": "",
						"income": "450000",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Proposer annual income | 450000";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”							Channel K means SPARC
						IF Channel IN(K, CAT) AND 					
						Case 4.a:	IF AnnualIncome(proposer) < MinimumIncome(from DB)-500000 AND Education(proposer) NOT “underGraduateEducations” THEN Msg

*/

function channelSparc_EducationNotUnderGraduate_AllowCase(output) {  //channel=K,income-500000 /NO Message:
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "Graduate",
						"insurerEducation": "",
						"income": "500000",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”							Channel K means SPARC
						IF Channel IN(K, CAT) AND 					
						Case 4.b:	IF Education(proposer)=“High School” AND AnnualIncome(proposer)<MinimumHighSchoolIncome(from DB)-1000000 THEN Msg				

*/
function channelSparc_EducationHighSchool_RejectCase(output) {  //channel=K,income-400000 /Message://Proposer annual income | 400000
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "High School",
						"insurerEducation": "",
						"income": "400000",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Proposer annual income | 400000";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”							Channel K means SPARC
						IF Channel IN(K, CAT) AND 					
						Case 4.b:	IF Education(proposer)=“High School” AND AnnualIncome(proposer)<MinimumHighSchoolIncome(from DB)-1000000 THEN Msg				

*/
function channelSparc_EducationHighSchool_AllowCase(output) {  //channel=K,income-1000001 /No Message: 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "High School",
						"insurerEducation": "",
						"income": "1000001",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
     }
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”	
					 IF Channel NOT IN(K, CAT) AND		
						IF customerClassification NOT IN exceptionalCustomerClassification AND	
						Case a:	IF AnnualIncome(proposer) < MinimumIncome(from DB)-500000 AND Education(proposer) NOT IN“underGraduateEducations” THEN Msg
*/

function channelA_EducationNotUnderGraduate_RejectCase(output) {  //channel=K,income-490000 /No Message: 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "GRADUATE",
						"insurerEducation": "",
						"income": "490000",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Proposer annual income | 490000";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”	
					 IF Channel NOT IN(K, CAT) AND			
						IF customerClassification NOT IN exceptionalCustomerClassification AND	
						Case a:	IF AnnualIncome(proposer) < MinimumIncome(from DB)-500000 AND Education(proposer) NOT IN“underGraduateEducations” THEN Msg
*/

function channelA_EducationNotUnderGraduate_AllowCase(output) {  //channel=A,income-500000 /No Message: 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "GRADUATE",
						"insurerEducation": "",
						"income": "500000",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”	
					 IF Channel NOT IN(K, CAT) AND		
						IF customerClassification NOT IN exceptionalCustomerClassification AND	
						Case b:	IF AnnualIncome(proposer) < MinimumHighSchoolIncome(from DB)-1000000 AND Education(proposer) = “High School” THEN Msg
*/

function channelA_EducationHighSchool_RejectCase(output) {  //channel=K,income-990000 /No Message: 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "High School",
						"insurerEducation": "",
						"income": "990000",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Proposer annual income | 990000";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”	
					 IF Channel NOT IN(K, CAT) AND			
						IF customerClassification NOT IN exceptionalCustomerClassification AND	
						Case b:	IF AnnualIncome(proposer) < MinimumHighSchoolIncome(from DB)-1000000 AND Education(proposer) = “High School” THEN Msg
*/

function channelA_EducationHighSchool_AllowCase(output) {  //channel=A,income-1000000 /No Message: 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "High School",
						"insurerEducation": "",
						"income": "1000000",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}


/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”	
					 IF Channel NOT IN(K, CAT) AND				
						IF customerClassification IN exceptionalCustomerClassification AND	
									IF AnnualIncome(proposer) < MinimumIncome(from DB)-500000 THEN Msg
*/

function channelA_customerClassification_RejectCase(output) {  //channel=K,income-490000, education-No condition with edu /No Message: 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "Others",
						"insurerEducation": "",
						"income": "490000",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "X",
						"customerClassification":"SBPRV",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Proposer annual income | 490000";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: For  Msg: "Proposer annual income |  {proposerAnnualIncome}”	
					 IF Channel NOT IN(K, CAT) AND			
						IF customerClassification NOT IN exceptionalCustomerClassification AND	
						Case b:	IF AnnualIncome(proposer) < MinimumHighSchoolIncome(from DB)-1000000 AND Education(proposer) = “High School” THEN Msg
*/

function channelA_customerClassification_AllowCase(output) {  //channel=A,income-1000000 /No Message: 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "Others",
						"insurerEducation": "",
						"income": "1000000",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "X",
						"customerClassification":"SBPRV",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: 
For  Msg: "Spouse declared income should be greater than 10 Lakhs"		
		IF “Occupation(proposer)=HouseWife”  AND  “spouseAnnualIncome <= SpouseAnnualIncome based on channel(from DB)-10,00000” THEN  Msg
*/

function spouseIncome10LacsRejectCase(output) {  //occupation=Housewife, channel=A,income-990000 / Message: "Spouse declared income should be greater than 10 Lakhs" 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "GRADUATE",
						"insurerEducation": "",
						"income": "500000",
						"insurerAnnualIncome": "",
						"occupation": "Housewife",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "990000",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Spouse declared income should be greater than 10 Lakhs";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: 
For  Msg: "Spouse declared income should be greater than 10 Lakhs"		
		IF “Occupation(proposer)=HouseWife”  AND  “spouseAnnualIncome <= SpouseAnnualIncome based on channel(from DB)-10,00000” THEN  Msg
*/

function spouseIncome10LacsAllowCase(output) {  //occupation=Housewife, channel=A, spouseAnnualIncome-1000001 /No Message: 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "GRADUATE",
						"insurerEducation": "",
						"income": "500000",
						"insurerAnnualIncome": "",
						"occupation": "Housewife",
						"insurerOccupation": "",
						"sumAssured": "",
						"spouseAnnualIncome": "1000001",
						"spouseTotalInsuranceCover": "",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: 		
For  Msg: "Spouse insurance cover should be more than two times the proposer’s insurance cover"		
		IF “Occupation(proposer)=HouseWife”  AND  “spouseTotalInsuranceCover < 2*SumAssured” THEN  Msg
*/

function spouseTotalInsuranceCoverRejectCase(output) {  //occupation=Housewife, channel=A,sumAssured-200000, spouseTotalInsuranceCover-350000/ Message: "Spouse insurance cover should be more than two times the proposer’s insurance cover" 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "GRADUATE",
						"insurerEducation": "",
						"income": "500000",
						"insurerAnnualIncome": "",
						"occupation": "Housewife",
						"insurerOccupation": "",
						"sumAssured": "2500005",
						"spouseAnnualIncome": "1000005",
						"spouseTotalInsuranceCover": "4000000",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Spouse insurance cover should be more than two times the proposer’s insurance cover";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: 		
For  Msg: "Spouse insurance cover should be more than two times the proposer’s insurance cover"		
		IF “Occupation(proposer)=HouseWife”  AND  “spouseTotalInsuranceCover < 2*SumAssured” THEN  Msg
*/

function spouseTotalInsuranceCoverAllowCase(output) {  //occupation=Housewife, channel=A,sumAssured-200000, spouseTotalInsuranceCover-450000/ NO Message:  
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "GRADUATE",
						"insurerEducation": "",
						"income": "500000",
						"insurerAnnualIncome": "",
						"occupation": "Housewife",
						"insurerOccupation": "",
						"sumAssured": "2500005",
						"spouseAnnualIncome": "1000005",
						"spouseTotalInsuranceCover": "5100000",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "A",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}


/****
Rule for TestCase:: 		
For  Msg: "Sum Assured | {sumAssured}" 				
		IF Channel =K AND product=SMTP THEN		
			Case 1:	IF sumAssured <= MinimumSumAssured(from DB)-2500000 AND AnnualIncome(proposer)<=MinimumIncome (from DB)-500000 THEN Msg
			
	THIS CASE WILL NOT COME BEFORE IT "Proposer Income < 500000" REJECT IT prior  // channel=K,sumAssured-4900000, income-490000/ Message: "Sum Assured | 2400000.0"
*/
/**************************************************************************************************************************************************************/
/****
Rule for TestCase:: 		
For  Msg: "Sum Assured | {sumAssured}" 				
		IF Channel =K AND product=SMTP THEN		
			Case 1:	IF sumAssured <= MinimumSumAssured(from DB) AND AnnualIncome(proposer)<=MinimumIncome (from DB) THEN Msg

*/

function channelKSumAssuredIncomeAllowCase(output) { // channel=K,sumAssured-5100000, income-510000 / NO Message:  
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "GRADUATE",
						"insurerEducation": "",
						"income": "510000",
						"insurerAnnualIncome": "",
						"occupation": "Housewife",
						"insurerOccupation": "",
						"sumAssured": "2500005",
						"spouseAnnualIncome": "1000005",
						"spouseTotalInsuranceCover": "10300000",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: 		
For  Msg: "Sum Assured | {sumAssured}" 				
		IF Channel =K AND product=SMTP THEN		
			Case 2:	IF AnnualIncome(proposer) > MinimumIncome(from DB)-500000 AND sumAssured>AnnualIncome(proposer/Insured[if formTwo])*10 THEN Msg
*/

function channelKSumAssured10TimesIncomeRejectCase(output) { // channel=K,sumAssured-5200000, income-510000 / NO Message:  
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "GRADUATE",
						"insurerEducation": "",
						"income": "510000",
						"insurerAnnualIncome": "",
						"occupation": "SALARIED",
						"insurerOccupation": "",
						"sumAssured": "5200000",
						"spouseAnnualIncome": "1000005",
						"spouseTotalInsuranceCover": "10300000",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Sum Assured | 5200000.0";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}
	
/****
Rule for TestCase:: 		
For  Msg: "Sum Assured | {sumAssured}" 				
		IF Channel =K AND product=SMTP THEN		
			Case 2:	IF AnnualIncome(proposer) > MinimumIncome(from DB)-500000 AND sumAssured>AnnualIncome(proposer/Insured[if formTwo])*10 THEN Msg
*/

function channelKSumAssured10TimesIncomeAllowCase(output) { // channel=K,sumAssured-5200000, income-510000 / NO Message:  
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "GRADUATE",
						"insurerEducation": "",
						"income": "510000",
						"insurerAnnualIncome": "",
						"occupation": "SALARIED",
						"insurerOccupation": "",
						"sumAssured": "5100000",
						"spouseAnnualIncome": "1000005",
						"spouseTotalInsuranceCover": "10300000",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == true && messageBucketSize == 0;
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

		
/****
Rule for TestCase:: 		
For  Msg: "Sum Assured | {sumAssured}" 				
		IF Channel =K AND product=SMTP THEN
		IF “Occupation(proposer)=HouseWife”  AND  sumAssured< HouseWifeMinSumAssured(from DB) OR sumAssured>HousewifeMaxSumAssured(from DB) THEN  Msg

*/		
function channelKHouseWifeSumAssuredIncomeRejectCase(output) {  // channel=K,sumAssured-4900000, income-510000/ Message: "Sum Assured | 2400000.0" 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "GRADUATE",
						"insurerEducation": "",
						"income": "510000",
						"insurerAnnualIncome": "",
						"occupation": "Housewife",
						"insurerOccupation": "",
						"sumAssured": "2400000",
						"spouseAnnualIncome": "1000005",
						"spouseTotalInsuranceCover": "5100000",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Sum Assured | 2400000.0";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}

/****
Rule for TestCase:: 	
For  Msg: "Sum Assured | {sumAssured}" 		
		IF Channel =K AND product=SMTP THEN
			IF sumAssured < MinimumSumAssured(from DB) AND NOT Occupation(proposer)=HouseWife”   THEN Msg
*/

function channelKSumAssuredSalariedRejectCase(output) {  // channel=K,sumAssured-2400000, income-510000/ Message: "Sum Assured | 2400000.0" 
	let restrictionRequestPayload = {
		"request": {
			"metadata": {
				"env": "dev",
				"requestId": "c54d55e0-958c-11e8-80c4-b1b8d987c4df"
			},
			"requestData": {
				"requestPayload": {
					"productRestrictionPayload": {
						"education": "GRADUATE",
						"insurerEducation": "",
						"income": "510000",
						"insurerAnnualIncome": "",
						"occupation": "Salaried",
						"insurerOccupation": "",
						"sumAssured": "2400000",
						"spouseAnnualIncome": "1000005",
						"spouseTotalInsuranceCover": "5100000",
						"productId": "154",
						"agentId": "126191",
						"panNumber": "ASDPH9918H",
						"formType": "self",
						"isCIRider": false,
						"communicationPinCode": "122015",
						"communicationCountry": "India",
						"communicationState": "HARYANA",
						"agentRole": "AA",
						"communicationCity": "Gurugram",
						"isSmoker": false,
						"nationality": "indian",
						"dateOfBirth": "2003-02-28T18:30:00.000+0000",
						"channel": "K",
						"residentialStatus": "indian",
						"isDedupeValidated": "false",
						"isWOPPlusRider": "",
						"permanentCountry": "India",
						"isDiabetic": false,
						"transactionId": 1500120038,
						"id": "604f081746e0fb00012d0a10",
						"insurerDateOfBirth": ""
					}
				}
			}
		}
	}

	if (output != 0) {
		let flag = output.response.responseData.responsePayload.eligible;
		let messageBucketSize = output.response.responseData.responsePayload.messages.length;
		let status = flag == false && messageBucketSize == 1 && output.response.responseData.responsePayload.messages[0] == "Sum Assured | 2400000.0";
		let msgText = "";
		if(messageBucketSize>0){
			msgText=output.response.responseData.responsePayload.messages[0];
		}
		return { "productId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.productId, 
				 "transactionId": restrictionRequestPayload.request.requestData.requestPayload.productRestrictionPayload.transactionId, 
				 "status": status,
				 "msgText":msgText};
	}
	return restrictionRequestPayload;
}			

		