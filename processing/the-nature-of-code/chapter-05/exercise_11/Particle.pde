class Particle implements Collision {

  Body body;
  float r;
  color col;

  Particle(float x, float y, float r_) {
    r = r_;

    makeBody(x, y, r);
    body.setUserData(this);
    col = color(127);
  }

  void killBody() {
    box2d.destroyBody(body);
  }

  void change() {
    col = color(255, 0, 0);
  }

  boolean done() {
    Vec2 pos = box2d.getBodyPixelCoord(body);
    if (pos.y > height+r*2) {
      killBody();
      return true;
    }
    return false;
  }

  void display() {
    Vec2 pos = box2d.getBodyPixelCoord(body);
    float a = body.getAngle();

    pushMatrix();

    translate(pos.x, pos.y);
    rotate(a);

    fill(col);
    stroke(0);
    strokeWeight(2);

    ellipse(0, 0, r*2, r*2);
    line(0, 0, r, 0);

    popMatrix();
  }

  void makeBody(float x, float y, float r) {
    BodyDef bd = new BodyDef();
    bd.position = box2d.coordPixelsToWorld(x, y);
    bd.type = BodyType.DYNAMIC;
    body = box2d.createBody(bd);

    CircleShape cs = new CircleShape();
    cs.m_radius = box2d.scalarPixelsToWorld(r);

    FixtureDef fd = new FixtureDef();
    fd.shape = cs;
    fd.density = 1;
    fd.friction = 0.01;
    fd.restitution = 0.3;

    body.createFixture(fd);
    body.setAngularVelocity(random(-10, 10));
  }
}