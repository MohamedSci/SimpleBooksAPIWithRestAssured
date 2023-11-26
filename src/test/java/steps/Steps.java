package steps;

import java.lang.reflect.*;
import java.util.*;

import com.github.javafaker.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import helper.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Steps {
	public static final String BASE_URL = "https://simple-books-api.glitch.me";

	public static String token;
	public static Response response;
	public static String jsonString;
	public static String bookId;
	public static String ordrId;
	public static String bookName;
	public static String bookType;
	public static String bookAvailable;
	BookClo bookIns;
	List<BookClo> bookListEx;
//	Java convert JSON String  has list of json objects to equivalent list of Java class Object.
	public static <T> List<T> stringJsonToObjectArray(String s, Class<T[]> clazz) {
		T[] arr = new Gson().fromJson(s, clazz);
		return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
	}
	//	Java convert JSON String  has list of json objects to equivalent list of Java class Object.
	public static <T> T stringToObject(String s, Class<T> clazz) {
        return new Gson().fromJson(s, clazz); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
	}
	@Given("I am an authorized User")
	public void iAmAnAuthorizedUser() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Map<String, String> mapAuth = new HashMap<>();
		 Faker fakeAuth = new Faker();
		 final String CLIENTNAME = fakeAuth.artist().name();
		 final String CLIENTEMAIL = fakeAuth.internet().emailAddress();
		mapAuth.put("clientName", CLIENTNAME);
		mapAuth.put("clientEmail", CLIENTEMAIL);
		JSONObject requestParams = new JSONObject(mapAuth);
		response = request.body(requestParams).post("/api-clients");
		token = response.jsonPath().get("accessToken");
		Assert.assertEquals(201, response.getStatusCode());
	}

	@Then("Lets Check the Status From API")
	public void letsCheckTheStatusFromAPI() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "Application/json");
		response = request.get("/status");
		String jsonString = response.asString();
		String ResponseMsgTxt = JsonPath.from(jsonString).get("/status/");
		Assert.assertEquals(ResponseMsgTxt, "OK");
		Assert.assertEquals(200, response.getStatusCode());
	}

	@Given("A list of Books are available")
	public void aListOfBooksAreAvailable() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.get("/books/");
		Assert.assertEquals(200, response.getStatusCode());
	}

	@Then("The User stores the data of a certain Book")
	public void userStoresTheDataOfACertainBook() {
		bookListEx = stringJsonToObjectArray(response.asString(), BookClo[].class);
		bookIns = bookListEx.get(0);
		System.out.println("bookCl.getId(): " + bookIns.getId());
		System.out.println("bookCl.getName() : " + bookIns.getName());
		System.out.println("bookCl.getType() " + bookIns.getType());
		System.out.println("bookCl.getAvailable() " + bookIns.getAvailable());
	}

	@Given("The User request Single Book APi")
	public void userRequestSingleBookAPi() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.get("/books/" + bookIns.getId());
		Assert.assertEquals(200, response.getStatusCode());
	}

	@Then("The Validation of response data retrieved from ALL Books and Single Books APIs are the Identical")
	public void validationOfResponseDataRetrievedFromALLBooksandSingleBooksAPIsAreTheIdentical() {
		jsonString = response.asString();
		BookClo bookSingle = stringToObject(jsonString,BookClo.class);
		Assert.assertEquals(bookSingle.getId(), bookIns.getId());
		Assert.assertEquals(bookSingle.getName(), bookIns.getName());
		Assert.assertEquals(bookSingle.getType(), bookIns.getType());
		Assert.assertEquals(bookSingle.getAvailable(), bookIns.getAvailable());
	}

	@When("The User submits a Book to be added to his Orders list")
	public void userSubmitsBookToBeAddedToHisOrdersList() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");
		Map<String, String> mapOrders = new HashMap<>();
		mapOrders.put("bookId", String.valueOf(bookIns.getId()));
		Faker fakeCat = new Faker();
		mapOrders.put("customerName", fakeCat.cat().name());
		JSONObject requestParamsOrders = new JSONObject(mapOrders);
		response = request.body(requestParamsOrders).post("/orders");
		jsonString = response.asString();
		Map<String, String> order = JsonPath.from(jsonString).get();
		ordrId = order.get("orderId");
	}

	@Then("The API request informs that the Book is added and Order Id is stored")
	public void APIRequestInformsThatTheBookIsAddedAndOrderIdIsStored() {
		Assert.assertEquals(201, response.getStatusCode());
	}

	@Given("A list of all  Orders are available")
	public void istOfAllOrdersAreAvailable() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");
		response = request.get("/orders");
		Assert.assertEquals(200, response.getStatusCode());
	}

	@Then("The User Checks the recently Submitted Order to be found in the response, the Order is found")
	public void userChecksTheRecentlySubmittedOrderToBeFoundInTheResponseTheOrderIsFound() {
		jsonString = response.asString();
		List<Map<String, String>> booksOfUser = JsonPath.from(jsonString).get("orders");
        Assert.assertFalse(booksOfUser.isEmpty());
		ArrayList<Object> orderIdsList = new ArrayList<>();
        for (Map<String, String> stringStringMap : booksOfUser) {
            orderIdsList.add(stringStringMap.get("id"));
        }
		Assert.assertTrue(orderIdsList.contains(ordrId));
	}

	@Given("The User request Single Order APi")
	public void userRequestSingleOrderAPi() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");
		response = request.get("/orders/" + ordrId);
		Assert.assertEquals(200, response.getStatusCode());
	}

	@Then("The User Checks the recently Submitted Order to be found in the Single Order response, the Order is found")
	public void userChecksTheRecentlySubmittedOrderToBeFoundInTheSingleOrderResponseTheOrderIsFound() {
		jsonString = response.asString();
		Map<String, String> booksOfUser = JsonPath.from(jsonString).get("order");
        Assert.assertEquals(booksOfUser.get("id"), ordrId);
	}

	@When("The User updates a Book from his Orders list using the Book s Order id")
	public void userUpdatesABookFromHisOrdersListUsingTheBooksOrderId() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");
		Map<String, String> mapOrderReq = new HashMap<>();
		Faker fakeBear = new Faker();
		mapOrderReq.put("customerName", fakeBear.beer().name());
		JSONObject requestParamsOrderReq = new JSONObject(mapOrderReq);
		response = request.body(requestParamsOrderReq).patch("/orders/" + ordrId);
	}

	@Then("The Book is updated")
	public void bookIsUpdated() {
		Assert.assertEquals(204, response.getStatusCode());
	}

	@When("The User removes a Book from his Orders list using the Book s Order id")
	public void userRemovesABookFromHisOrdersListUsingTheBooksOrderId() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");
		response = request.delete("/orders/" + ordrId);
	}

	@Then("The Book is deleted")
	public void bookIsDeleted() {
		Assert.assertEquals(204, response.getStatusCode());
	}

	@Given("A list of all  Orders are available Again")
	public void istOfAllOrdersAreAvailableAgain() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");
		response = request.get("/orders");
		Assert.assertEquals(200, response.getStatusCode());
	}

@Then("The User Checks Again the recently Submitted Order to be found in the response, the Order is Missing")
public void userChecksAgainTheRecentlySubmittedOrderToBeFoundInTheResponseTheOrderIsFound() {
	jsonString = response.asString();
	List<Map<String, String>> booksOfUser = JsonPath.from(jsonString).get("orders");
	Assert.assertEquals(booksOfUser.size(),0);
	ArrayList<Object> orderIdsList = new ArrayList<>();
    for (Map<String, String> stringStringMap : booksOfUser) {
        orderIdsList.add(stringStringMap.get("id"));
    }
Assert.assertFalse(orderIdsList.contains(ordrId));
}

	@Given("The User request Single Order APi Again")
	public void userRequestSingleOrderAPiAgain() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");
		response = request.get("/orders/" + ordrId);
		Assert.assertEquals(200, response.getStatusCode());
	}




@Then("The User Checks Again the recently Submitted Order to be found in the Single Order response, the Order is Missing")
public void userChecksAgainTheRecentlySubmittedOrderToBeFoundInTheSingleOrderResponseTheOrderIsFound() {
	jsonString = response.asString();
	Map<String, String> booksOfUser = JsonPath.from(jsonString).get("order");
Assert.assertNotSame(booksOfUser.get("id"),ordrId);
}

}
