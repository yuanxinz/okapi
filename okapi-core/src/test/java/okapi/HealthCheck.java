/*
 * Copyright (c) 2015-2016, Index Data
 * All rights reserved.
 * See the file LICENSE for details.
 */
package okapi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import static com.jayway.restassured.RestAssured.*;
import io.vertx.core.json.JsonObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class HealthCheck {
  Vertx vertx;

  private final int port = Integer.parseInt(System.getProperty("port", "9130"));

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
    vertx = Vertx.vertx();

    DeploymentOptions opt = new DeploymentOptions()
            .setConfig(new JsonObject().put("storage", "inmemory"));
    vertx.deployVerticle(MainVerticle.class.getName(), opt);
  }

  @After
  public void tearDown() {
    vertx.close();
  }

  @Test
  public void test1() {
    given().port(port).get("/_/health").then().assertThat().statusCode(200);
    given().port(port).get("/_/health2").then().assertThat().statusCode(404);
  }
}
