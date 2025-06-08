package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.RestAssured.with;


public class Specs {
        public static RequestSpecification requestSpec = with()
                .filter(withCustomTemplates())
                .contentType("application/json")
                .log().all();

        public static ResponseSpecification responseSpec(int statusCode) {
            return new ResponseSpecBuilder()
                    .expectStatusCode(statusCode)
                    .log(LogDetail.ALL)
                    .build();
        }
}