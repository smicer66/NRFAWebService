package com.probase.nrfa.enumerations;

public enum CardStatus {
	//Activated Level 1: Activated by NFRA Internal Process on Card Generation
	//Activated Level 2: Activated by Bank on Issuing to Distributor
	//Activated Level 3: Activated by Distributor on Issuing to Customer
    ACTIVATED_LEVEL_1, ACTIVATED_LEVEL_2, ACTIVATED_LEVEL_3, INACTIVE, DISABLED, DELETED
}
