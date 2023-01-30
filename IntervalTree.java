public class IntervalTree<T extends Comparable<T>> {

    private Node<T> newNode = new Node<T>();
    private Node<T> root = newNode;

    public IntervalTree() {
        root.leftNode = newNode;
        root.rightNode = newNode;
        root.parentNode = newNode;
    }

    class Node<T extends Comparable<T>> {

        public T nodeId;
        public T xLow;
        public T yLow;
        public T xHigh;
        public T yHigh;
        public int color;

        Node<T> parentNode;
        Node<T> leftNode;
        Node<T> rightNode;

        Node(T xLow) {
            this.xLow = xLow;
        }

        Node() {
            nodeId = null;
            xHigh = null;
            xHigh = null;
            yLow = null;
            color = 1;
            parentNode = null;
            leftNode = null;
            rightNode = null;
        }
    }
    
    private void rotateRight(Node<T> node) {
        Node<T> x = node.leftNode;
        node.leftNode = x.rightNode;

        if (x.rightNode != newNode)
            x.rightNode.parentNode = node;

        x.parentNode = node.parentNode;

        if (node.parentNode == newNode)
            root = x;
        else if (node.parentNode.rightNode == node)
            node.parentNode.rightNode = x;
        else
            node.parentNode.leftNode = x;

        x.rightNode = node;
        node.parentNode = x;
    }
    
    private void rotateLeft(Node<T> node) {
        Node<T> x;
        x = node.rightNode;
        node.rightNode = x.leftNode;

        if (x.leftNode != newNode)
            x.leftNode.parentNode = node;

        x.parentNode = node.parentNode;

        if (node.parentNode == newNode)
            root = x;
        else if (node.parentNode.leftNode == node)
            node.parentNode.leftNode = x;
        else
            node.parentNode.rightNode = x;

        x.leftNode = node;
        node.parentNode = x;
    }
    
    public void insertNode(T nodeId, T xLow, T yLow, T xHigh, T yHigh) {
        insertNodeHelper(nodeId, new Node<T>(xLow), yLow, xHigh, yHigh);
    }

    private void insertNodeHelper(T nodeId, Node<T> xLow, T yLow, T xHigh, T yHigh) {
        xLow.yLow = yLow;
        xLow.xHigh = xHigh;
        xLow.yHigh = yHigh;
        xLow.nodeId = nodeId;

        Node<T> parentNode = newNode;
        Node<T> x = root;

        Node<T> y = newNode;
        Node<T> z = xLow;

        while (x != newNode) {
            parentNode = x;
            if (xLow.xLow.compareTo(x.xLow) < 0)
                x = x.leftNode;
            else
                x = x.rightNode;
        }
        xLow.parentNode = parentNode;

        if (parentNode == newNode)
            root = xLow;
        else if (xLow.xLow.compareTo(parentNode.xLow) < 0)
            parentNode.leftNode = xLow;
        else
            parentNode.rightNode = xLow;

        xLow.leftNode = newNode;
        xLow.rightNode = newNode;
        xLow.color = 0;

        while (z.parentNode.color == 0) {
            if (z.parentNode == z.parentNode.parentNode.leftNode) {
                y = z.parentNode.parentNode.rightNode;

                if (y.color == 0) {
                    z.parentNode.color = 1;
                    y.color = 1;
                    z.parentNode.parentNode.color = 0;
                    z = z.parentNode.parentNode;
                } else if (z == z.parentNode.rightNode) {
                    z = z.parentNode;
                    rotateLeft(z);
                } else {
                    z.parentNode.color = 1;
                    z.parentNode.parentNode.color = 0;
                    rotateRight(z.parentNode.parentNode);
                }
            } else {
                y = z.parentNode.parentNode.leftNode;

                if (y.color == 0) {
                    z.parentNode.color = 1;
                    y.color = 1;
                    z.parentNode.parentNode.color = 0;
                    z = z.parentNode.parentNode;
                } else if (z == z.parentNode.leftNode) {
                    z = z.parentNode;
                    rotateRight(z);
                } else {
                    z.parentNode.color = 1;
                    z.parentNode.parentNode.color = 0;
                    rotateLeft(z.parentNode.parentNode);
                }
            }
        }
        root.color = 1;
    }
    
    public boolean checkOverlap(T nodeId, T key, T yLow, T xHigh, T yHigh) {
        Node<T> current = root;
        boolean isNoOverlap = true;
        while (current != newNode) {
            if (current.xLow.compareTo(key) < 0 && current.xHigh.compareTo(key) < 0 && current.xLow.compareTo(xHigh) < 0 && current.xHigh.compareTo(xHigh) < 0
                    || current.yLow.compareTo(yLow) < 0 && current.yHigh.compareTo(yLow) < 0 && current.yLow.compareTo(yHigh) < 0 && current.yHigh.compareTo(yHigh) < 0
                    || current.xLow.compareTo(key) > 0 && current.xHigh.compareTo(key) > 0 && current.xLow.compareTo(xHigh) > 0 && current.xHigh.compareTo(xHigh) > 0
                    || current.yLow.compareTo(yLow) > 0 && current.yHigh.compareTo(yLow) > 0 && current.yLow.compareTo(yHigh) > 0 && current.yHigh.compareTo(yHigh) > 0) {
                if (current.xLow.compareTo(key) < 0)
                    current = current.rightNode;
                else
                    current = current.leftNode;
            } else {
                isNoOverlap = false;
                System.out.println("-----------------");
                System.out.println("| Overlap found |");
                System.out.println("-----------------");
                System.out.println("Rectangles with id: " + current.nodeId + " and " + nodeId + " are overlaping");
                break;
            }
        }

        return isNoOverlap;
    }
    
    public void deleteNode(T data) {
        deleteNodeHelper(this.root, data);
    }

    private void deleteNodeHelper(Node<T> node, T key) {
        Node<T> z = newNode;
        Node<T> x, y;
        while (node != newNode) {
            if (node.xLow.compareTo(key) < 0)
                z = node;
            if (node.xLow.equals(key))
                node = node.rightNode;
            else
                node = node.leftNode;
        }

        if (z == newNode) {
            System.out.println("Couldn't find key in the tree");
            return;
        }

        y = z;
        int yOriginalColor = y.color;

        if (z.leftNode == newNode) {
            x = z.rightNode;
            treeTransplant(z, z.rightNode);
        } else if (z.rightNode == newNode) {
            x = z.leftNode;
            treeTransplant(z, z.leftNode);
        } else {
            y = minimum(z.rightNode);
            yOriginalColor = y.color;
            x = y.rightNode;
            if (y.parentNode == z)
                x.parentNode = y;
            else {
                treeTransplant(y, y.rightNode);
                y.rightNode = z.rightNode;
                y.rightNode.parentNode = y;
            }

            treeTransplant(z, y);
            y.leftNode = z.leftNode;
            y.leftNode.parentNode = y;
            y.color = z.color;
        }
        if (yOriginalColor == 0)
            fixDelete(x);
    }

    private void treeTransplant(Node<T> x, Node<T> y) {
        if (x.parentNode == null) 
            root = y;
        else if (x == x.parentNode.leftNode)
            x.parentNode.leftNode = y;
        else 
            x.parentNode.rightNode = y;
        y.parentNode = x.parentNode;
    }

    public Node<T> minimum(Node<T> node) {
        while (node.leftNode != newNode) 
            node = node.leftNode;
        
        return node;
    }

    private void fixDelete(Node<T> node) {
        Node<T> s;
        while (node != root && node.color == 0) {
            if (node == node.parentNode.leftNode) {
                s = node.parentNode.rightNode;
                if (s.color == 1) {
                    s.color = 0;
                    node.parentNode.color = 1;
                    rotateLeft(node.parentNode);
                    s = node.parentNode.rightNode;
                }

                if (s.leftNode.color == 0 && s.rightNode.color == 0) {
                    s.color = 1;
                    node = node.parentNode;
                } else {
                    if (s.rightNode.color == 0) {
                        s.leftNode.color = 0;
                        s.color = 1;
                        rotateRight(s);
                        s = node.parentNode.rightNode;
                    }

                    s.color = node.parentNode.color;
                    node.parentNode.color = 0;
                    s.rightNode.color = 0;
                    rotateLeft(node.parentNode);
                    node = root;
                }
            } else {
                s = node.parentNode.leftNode;
                if (s.color == 1) {
                    s.color = 0;
                    node.parentNode.color = 1;
                    rotateRight(node.parentNode);
                    s = node.parentNode.leftNode;
                }

                if (s.rightNode.color == 0 && s.rightNode.color == 0) {
                    s.color = 1;
                    node = node.parentNode;
                } else {
                    if (s.leftNode.color == 0) {
                        s.rightNode.color = 0;
                        s.color = 1;
                        rotateLeft(s);
                        s = node.parentNode.leftNode;
                    }

                    s.color = node.parentNode.color;
                    node.parentNode.color = 0;
                    s.leftNode.color = 0;
                    rotateRight(node.parentNode);
                    node = root;
                }
            }
        }
        node.color = 0;
    }
}