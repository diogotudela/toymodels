import toymodels.*;
ToyTzara tzara;
String[] txt;
String result;
int margin;

void setup(){
  size(800,800);
  background(0,0,255);
  smooth();
  fill(255);
  textSize(36);
  result="";
  margin = 50;
  txt = loadStrings("gysin.txt");
  tzara = new ToyTzara(this);
  tzara.loadText(txt);
  
}


void draw(){
  background(0,0,255);
  text(result,margin,margin,width-2*margin,height-4*margin);
  footnote();
}

void keyPressed(){
  result = tzara.cutUp();
}

void footnote(){
  pushStyle();
  textAlign(LEFT,BOTTOM);
  textSize(10);
  text("Toy Models for Processing\nVersion Beta.20.30.1\nOriginal Text — \nBrion Gysin (1978) The Cut–Ups Self–Explained\nin The Third Mind",margin,height-margin);
  popStyle();
}