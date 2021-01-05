package oracle.banking.ba.obtfpm.transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import oracle.fsgbu.obtfpm.api.model.TransactionControllerStatus;

//import org.apache.commons.lang.StringUtils;
/*import org.json.JSONException;
import org.json.JSONObject;
//import org.json.XML;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;*/

import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

public class ObtfpmTransformer {
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ObtfpmTransformer.class);
 
	@SuppressWarnings("unchecked")
	/*public HashMap<String, Object> stringToMap(String data) {
		HashMap<String, Object> hm1 = new HashMap<String, Object>();
		hm1 = new Gson().fromJson(data, new HashMap<String, Object>().getClass());
		ObjectMapper objectMapper = new ObjectMapper();
		 try {
			 System.out.println("hm1"+hm1);
	            String json = objectMapper.writeValueAsString(hm1);
	            System.out.println("json"+json);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
		return json;
	}*/
	
	public String mapToString(HashMap<String, Object> data) {	// use toString()
		
		
		
		String msg = "\"{";
		int i = 0;
		System.out.println("data"+data);
		List<String> al1 = new ArrayList<String>(data.keySet());
		System.out.println("al1"+al1);
		for (String key : al1) {
			msg += key + ":";
			msg += data.get(key);
			if (i != (al1.size() - 1)) {
				msg += ",";
			}
			i++;
		}
		msg += "}\"";
		System.out.println("msg"+msg);
		return msg;
	}
	
	@SuppressWarnings("unchecked")
	public String mapToJsonString(HashMap<String, Object> data) {	// use toString()
		
		List<String> services = this.discoveryClient.getServices();
	    List<ServiceInstance> instances = new ArrayList<ServiceInstance>();
	    services.forEach(serviceName -> {
	        this.discoveryClient.getInstances(serviceName).forEach(instance ->{
	            //instances.add(instance);
	        	LOGGER.info("****serviceName***** {} ", serviceName);
	        	System.out.println("****serviceName***** {} "+ serviceName);
	            
	        	LOGGER.info("instance.getHost {} ",instance.getHost());
	            System.out.println("instance.getHost {} "+instance.getHost());
	            
	            LOGGER.info("instance.getInstanceId {} ",instance.getInstanceId());
	            System.out.println("instance.getInstanceId {} "+instance.getInstanceId());
	            
	            LOGGER.info("instance.getPort {} ",instance.getPort());
	            System.out.println("instance.getPort {} "+instance.getPort());
	            
	            LOGGER.info("instance.getServiceId {} ",instance.getServiceId());
	            System.out.println("instance.getServiceId {} "+instance.getServiceId());
	            
	            LOGGER.info("instance.getUri {} ",instance.getUri());
	            System.out.println("instance.getUri {} "+instance.getUri());
	        });
	    });
		
		ObjectMapper objectMapper = new ObjectMapper();
		  String json=null;
		  String jsonLC=null;
		 try {
			 System.out.println("hm1:::::::::::::"+data);
	             json = objectMapper.writeValueAsString(data);
	            System.out.println("in loop json:::::::::::::"+json);
	            
	            jsonLC = objectMapper.writeValueAsString(data.get("letterOfCreditDetails"));
	            System.out.println("in loop json LC Details :::::::::::::"+jsonLC);
	           // props.put("datasegmentResponse",  objectMapper.writeValueAsString(data.get("partyDetails")));
	          //  System.out.println("datasegmentResponse out json:::::::::::::"+props.get("datasegmentResponse"));
	            
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
		 System.out.println("out json:::::::::::::"+json);
		 
		 System.out.println("LC Details json:::::::::::::"+jsonLC);
		 
		return jsonLC;
	
	}
	
	public String mapToJsonPartyDetails(HashMap<String, Object> data) {	// use toString()
		ObjectMapper objectMapper = new ObjectMapper();
		  String json=null;
		  String jsonParty=null;
		  String domainId=null;
		 try {
			 System.out.println("hm1:::::::::::::"+data);
	           
	            System.out.println("in loop json:::::::::::::"+json);
	            domainId = objectMapper.writeValueAsString(data.get("domainId"));
	            jsonParty="";
	            jsonParty=jsonParty.concat("{\n"
	            		+ "    \"domainId\": "+domainId+",\n"
	            		+ "    \"branchCode\":\"PK2\",\n"
	            		+ "    \"parties\":");
	            jsonParty =    jsonParty.concat(objectMapper.writeValueAsString(data.get("partyDetails")));
	            jsonParty=jsonParty.concat("}\n");
	            System.out.println("in loop json Party Details :::::::::::::"+jsonParty);
	            
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
		 System.out.println("out json:::::::::::::"+json);
		
		 System.out.println("PArty Details json:::::::::::::"+jsonParty);
		return jsonParty;
	
	}
	
	public String mapToJsonTxnControllerDetails(HashMap<String, Object> data) {	// use toString()
		ObjectMapper objectMapper = new ObjectMapper();
		  String json=null;
		  String jsontxnCmn=null;
		  String dataSegmentCode=null;
		 try {
			 System.out.println("IN txnController:::::::::::::"+data);
	           
			 dataSegmentCode = objectMapper.writeValueAsString(data.get("dataSegmentCode"));
			 
			 
			 jsontxnCmn="";
			 jsontxnCmn=jsontxnCmn.concat("{\n"
	            		+ "    \"StatusModel\":{"
	            		+ "    \"code\": "+dataSegmentCode+",\n"
	            		+ "    \"status\":"+TransactionControllerStatus.COMPLETE+"\n"
	            		+ "    \"}\n");
	            
	            System.out.println("in loop json txnController Details :::::::::::::"+jsontxnCmn);
	        	
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
		 System.out.println("out json:::::::::::::"+json);
		
		 System.out.println("txnController Details json:::::::::::::"+jsontxnCmn);
		return jsontxnCmn;
	
	}
	
	/*public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> map = new HashMap<>();
		/*map.put("appId", "CMNCORE");
		map.put("Accept", "application/json");
		map.put("Content-Type", "application/json");
		map.put("branchCode", "203");
		map.put("userId", "SIVA01");
		//AdapterConnectionUtility adapterConnectionUtility = new AdapterConnectionUtility();
		//adapterConnectionUtility.restTemplate = restTemplate;
		StatusModel statusModel=new StatusModel();
		statusModel.setCode("fsgbu-ob-tfpm-vp-ilcimain");
		statusModel.setStatus(TransactionControllerStatus.COMPLETE);
		System.out.println(JSON.parse(statusModel.toString()));*/
		
		/*
		 * ResponseEntity<String> response = adapterConnectionUtility.callService(
		 * "http://10.40.137.113:8004/cmc-transactioncontroller-services/web/v1/transactioncontroller/PK2ILCI000042530/dsstatus/fsgbu-ob-tfpm-vp-ilcimain",
		 * AdapterConnectionUtility.PUT, map, String.class, statusModel);
		 */
		//System.out.println(response);
		/*
		 * System.out.println(response); System.out.println("Body" +
		 * response.getBody()); System.out.println("Default Priority" +
		 * response.getBody().getData().get(0).getDefaultPriorityId());
		 */
		

		// map.put("source", headerDTO.getSource()); System.out.println(obj);
		//System.out.println(map); // ResponseEntity response=
		//obj.getSystemDate(map, null);

		//System.out.println(response); // obj.mapAMLDetail(body, map);

	/*}*/
}