import toymodels.*;

ToyMarkov markov;
String[][] txts;
float[] proportions = {0.5,0.5};
String result;
int margin;

void setup(){
  size(800,800);
  background(0,0,255);
  smooth();
  fill(255);
  textSize(12);
  result="";
  margin = 50;
  txts = new String[2][];
  for(int i=0; i<=txts.length-1;i++){
    txts[i] = loadStrings("t"+i+".txt");
  }
  
  markov = new ToyMarkov(this);
  markov.loadTexts(txts,proportions," ",12);
}


void draw(){
  background(0,0,255);
  text(result.toUpperCase(),margin,margin,width-2*margin,height-5*margin);
  footnote();
}



void keyPressed(){
  result = markov.genText(markov.getRandomNGram(),100);
}


void footnote(){
  pushStyle();
  textAlign(LEFT,BOTTOM);
  textSize(10);
  text("Toy Models for Processing\nVersion Beta.20.30.1\nOriginal Text — \nNoam Chomsky (1957) An Elementary Linguistic Theory\nin Syntactic Structures\nand Reza Negarestani (2019) Langauge as Interaction as Computation\nin Intelligence and Spirit",margin,height-margin);
  popStyle();
}
