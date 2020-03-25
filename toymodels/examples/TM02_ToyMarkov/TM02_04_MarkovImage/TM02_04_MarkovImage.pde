import toymodels.*;
ToyMarkov markov;
PImage img, result;
int margin;

void setup(){
  size(800,800);
  background(0,0,255);
  smooth();
  
  img = loadImage("submission.jpg");
  markov = new ToyMarkov(this);
  markov.loadImg(img,8);
  result = markov.genImg(markov.getRandomNGram(),100,100,true);
}


void draw(){
  background(0,0,255);
  image(result,0,0);
  footnote();
}

void footnote(){
  pushStyle();
  fill(255);
  textAlign(LEFT,BOTTOM);
  textSize(10);
  text("Toy Models for Processing\nVersion Beta.20.30.1\nOriginal Image — \nGeorge Rouy (2017) Submission",margin,height-margin);
  popStyle();
}
