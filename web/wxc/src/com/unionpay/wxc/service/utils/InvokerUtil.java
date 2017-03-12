package com.unionpay.wxc.service.utils;

public class InvokerUtil {

	public static String writeQuery(String from,String to ,String queryValue){
		String hackathonQuery = "{"
				+"		     \"jsonrpc\": \"2.0\",                                                                  "
				+"		     \"method\": \"invoke\",                                                                "
				+"		     \"params\": {                                                                          "
				+"		         \"type\": 1,                                                                       "
				+"		         \"chaincodeID\": {                                                                 "
				+"		             \"name\": \"26267ecf373f393dd3d61ba060a65245894a607b81f6bf1615671274caaf838b081ac10b00c8438be3f2e8992a59d17f65306aae3b551a9d4cc0220d83f4d1f6\"    "
				+"		         },                                                                                 "
				+"		         \"ctorMsg\": {                                                                     "
				+"		             \"function\": \"putQuery\",                                                        "
				+"		             \"args\": [                                                                    "
				+"		                 \""+from+"\",                                                                     "
				+"		                 \""+to+"\",                                                                     "
				+"		                 \""+queryValue+"\"                                                                    "
				+"		             ]                                                                              "
				+"		         },                                                                                 "
				+"		         \"secureContext\": \"admin\"                                                       "
				+"		     },                                                                                     "
				+"		     \"id\": 1	      																		"																			
				+"		 }";
		return hackathonQuery;
		
	}
	
	public static String readQuery(String to ){
		String hackathonQuery = "{"
				+"		     \"jsonrpc\": \"2.0\",                                                                  "
				+"		     \"method\": \"query\",                                                                "
				+"		     \"params\": {                                                                          "
				+"		         \"type\": 1,                                                                       "
				+"		         \"chaincodeID\": {                                                                 "
				+"		             \"name\": \"26267ecf373f393dd3d61ba060a65245894a607b81f6bf1615671274caaf838b081ac10b00c8438be3f2e8992a59d17f65306aae3b551a9d4cc0220d83f4d1f6\"    "
				+"		         },                                                                                 "
				+"		         \"ctorMsg\": {                                                                     "
				+"		             \"function\": \"getQuery\",                                                        "
				+"		             \"args\": [                                                                    "
				+"		                 \""+to+"\"                                                                    "
				+"		             ]                                                                              "
				+"		         },                                                                                 "
				+"		         \"secureContext\": \"admin\"                                                       "
				+"		     },                                                                                     "
				+"		     \"id\": 1	      																		"																			
				+"		 }";
		return hackathonQuery;
		
	}
	
	public static String writeResult(String from,String to ,String result){
		String hackathonResult = "{"
				+"		     \"jsonrpc\": \"2.0\",                                                                  "
				+"		     \"method\": \"invoke\",                                                                "
				+"		     \"params\": {                                                                          "
				+"		         \"type\": 1,                                                                       "
				+"		         \"chaincodeID\": {                                                                 "
				+"		             \"name\": \"26267ecf373f393dd3d61ba060a65245894a607b81f6bf1615671274caaf838b081ac10b00c8438be3f2e8992a59d17f65306aae3b551a9d4cc0220d83f4d1f6\"    "
				+"		         },                                                                                 "
				+"		         \"ctorMsg\": {                                                                     "
				+"		             \"function\": \"putResult\",                                                        "
				+"		             \"args\": [                                                                    "
				+"		                 \""+from+"\",                                                                     "
				+"		                 \""+to+"\" ,                                                                    "				
				+"		                 \""+result+"\" ,                                                                    "				
				+"		             ]                                                                              "
				+"		         },                                                                                 "
				+"		         \"secureContext\": \"admin\"                                                       "
				+"		     },                                                                                     "
				+"		     \"id\": 1	      																		"																			
				+"		 }";
		return hackathonResult;
	}
	
	public static String readResult(String from ){
		String hackathonQuery = "{"
				+"		     \"jsonrpc\": \"2.0\",                                                                  "
				+"		     \"method\": \"query\",                                                                "
				+"		     \"params\": {                                                                          "
				+"		         \"type\": 1,                                                                       "
				+"		         \"chaincodeID\": {                                                                 "
				+"		             \"name\": \"26267ecf373f393dd3d61ba060a65245894a607b81f6bf1615671274caaf838b081ac10b00c8438be3f2e8992a59d17f65306aae3b551a9d4cc0220d83f4d1f6\"    "
				+"		         },                                                                                 "
				+"		         \"ctorMsg\": {                                                                     "
				+"		             \"function\": \"getResult\",                                                        "
				+"		             \"args\": [                                                                    "
				+"		                 \""+from+"\"                                                                    "
				+"		             ]                                                                              "
				+"		         },                                                                                 "
				+"		         \"secureContext\": \"admin\"                                                       "
				+"		     },                                                                                     "
				+"		     \"id\": 1	      																		"																			
				+"		 }";
		return hackathonQuery;
		
	}
}
