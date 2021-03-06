class Particle {

  PVector location;
  PVector velocity;
  PVector acceleration;
  float lifespan;

  Particle(PVector l) {
    location = l.copy();
    acceleration = new PVector();
    velocity = new PVector();
    lifespan = 255.0;
  }

  void run() {
    update();
    display();
  }

  void update() {
    velocity.add(acceleration);
    location.add(velocity);
    lifespan -= 2.0;
    acceleration.mult(0);
  }

  void applyForce(PVector force) {
    PVector f = force.copy();
    acceleration.add(f);
  }

  void display() {
    stroke(0, lifespan);
    fill(0, lifespan);
    ellipse(location.x, location.y, 8, 8);
  }

  boolean isDead() {
    if (lifespan < 0.0) {
      return true;
    } else {
      return false;
    }
  }
}