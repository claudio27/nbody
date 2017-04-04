package nbody.model;


import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BodyTest {
    private final double EXPECTED_FORCE = 6673;
    private final double BODY_MASS = 1e10;
    private final double BODY_DISTANCE = 1e3;
    private Body body;
    private Body other;

    @Before
    public void setUp() {
        body = new Body();
        body.location = new Vector2D(0, 0);
        body.mass = BODY_MASS;
        other = new Body();
        other.location = new Vector2D(0, 0);
        other.mass = BODY_MASS;
    }

    @Test
    public void calculateGravitationalForceAlongHorizontalAxis() {
        body.location.x = 0;
        body.location.y = 0;
        other.location.x = BODY_DISTANCE;
        other.location.y = 0;

        Vector2D bodyForce = body.calculateGravitationalForce(other);
        assertEquals(0, bodyForce.y, 0.01);
        assertEquals(EXPECTED_FORCE, bodyForce.x, 0.01);

        Vector2D otherForce = body.calculateGravitationalForce(other);
        assertEquals(0, otherForce.y, 0.01);
        assertEquals(EXPECTED_FORCE, otherForce.x, 0.01);
    }

    @Test
    public void calculateGravitationalForceAlongVerticalAxis() {
        body.location.x = 0;
        body.location.y = 0;
        other.location.x = 0;
        other.location.y = BODY_DISTANCE;

        Vector2D bodyForce = body.calculateGravitationalForce(other);
        assertEquals(0, bodyForce.x, 0.01);
        assertEquals(EXPECTED_FORCE, bodyForce.y, 0.01);

        Vector2D otherForce = body.calculateGravitationalForce(other);
        assertEquals(0, otherForce.x, 0.01);
        assertEquals(EXPECTED_FORCE, otherForce.y, 0.01);
    }

    @Test
    public void calculateGravitationalForceOtherAt45Deg() {
        body.location.x = 0;
        body.location.y = 0;
        other.location.x = Math.sin(Math.toRadians(45)) * BODY_DISTANCE;
        other.location.y = Math.cos(Math.toRadians(45)) * BODY_DISTANCE;
        Vector2D bodyForce = body.calculateGravitationalForce(other);
        assertEquals(EXPECTED_FORCE, bodyForce.length(), 0.01);
    }

    @Test
    public void calculateGravitationalForceOtherAt225Deg() {
        body.location.x = Math.sin(Math.toRadians(225)) * BODY_DISTANCE;
        body.location.y = Math.cos(Math.toRadians(225)) * BODY_DISTANCE;;
        other.location.x = 0;
        other.location.y = 0;
        Vector2D bodyForce = body.calculateGravitationalForce(other);
        assertEquals(EXPECTED_FORCE, bodyForce.length(), 0.01);
    }

    @Test
    public void calculateGravitationalAccelerationForEarth() {
        Body earth = new Body();
        earth.mass = CelestialBody.EARTH.mass;
        Body oneKg = new Body();
        oneKg.location = new Vector2D(0, CelestialBody.EARTH.radius);
        oneKg.mass = 1;
        assertEquals(9.82, Math.abs(oneKg.calculateGravitationalForce(earth).length()), 0.01);
    }

}
