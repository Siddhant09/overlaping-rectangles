JAVA = java
JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
Program.java\
IntervalTree.java\

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) */*.class

program:
	$(JAVA) Program