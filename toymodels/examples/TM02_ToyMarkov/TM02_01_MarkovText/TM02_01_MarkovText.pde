import toymodels.*;

ToyMarkov markov;
String[] txt;
String result;
int margin;

void setup(){
  size(800,800);
  background(0,0,255);
  smooth();
  fill(255);
  textSize(18);
  result="";
  margin = 50;
  txt = loadStrings("chomsky.txt");
  markov = new ToyMarkov(this);
  markov.loadText(txt," ",8);
}


void draw(){
  background(0,0,255);
  text(result.toUpperCase(),margin,margin,width-2*margin,height-4*margin);
  footnote();
}



void keyPressed(){
  result = markov.genText(markov.getRandomNGram(),200);
}








void footnote(){
  pushStyle();
  textAlign(LEFT,BOTTOM);
  textSize(10);
  text("Toy Models for Processing\nVersion Beta.20.30.1\nOriginal Text — \nNoam Chomsky (1957) An Elementary Linguistic Theory\nin Syntactic Structures",margin,height-margin);
  popStyle();
}
