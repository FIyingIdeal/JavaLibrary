package ThinkingInJava.Chapter10InnerClass.AnonymousInnerClass;

import ThinkingInJava.Chapter10InnerClass.Destination;

public class Parcel9 {

    public Destination destination(String dest) {
        return new Destination() {
            private String label = dest;
            @Override
            public String readLabel() {
                return this.label;
            }
        };
    }

    public static void main(String[] args) {
        Parcel9 p = new Parcel9();
        Destination destination = p.destination("ttt");
    }
}
