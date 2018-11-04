package structure;

import java.util.*;

/**
 * @author yanchao
 * @date 2018/10/31 19:21
 */
public class Tree {

    public TreeNode<String> createTree() {
        Scanner scanner = new Scanner(System.in);
        Queue<TreeNode<String>> queue = new LinkedList<>();
        PrintUtil.print("请输入根节点：");
        String data = scanner.nextLine();
        if (Objects.equals("", data.trim())) {
            return null;
        }
        TreeNode<String> tree = new TreeNode<>(data);
        queue.offer(tree);
        while (!queue.isEmpty()) {
            TreeNode<String> temp = queue.poll();
            PrintUtil.print("请输入 " + temp.data + " 的左节点和右节点并以逗号（,）分隔：");
            String leftAndRight = scanner.nextLine();
            // 没有输入任何有效内容，表示两个节点都没有，考虑不太全面，这里只考虑了空格，还有tab等其他需要考虑
            if ("".equals(leftAndRight.trim())) {
                continue;
            }
            // 注意split的用法，如果不添加第二个参数的话，输入的空字符将被忽略
            String[] leftAndRightArray = leftAndRight.split(",", -1);
            String left = leftAndRightArray[0].trim();
            // 有可能只有一个节点的情况下没有输入分隔符，这样的话这个节点将被作为左节点，有节点尾空
            String right = leftAndRightArray.length > 1 ? leftAndRightArray[1].trim() : "";
            if (!Objects.equals("", left)) {
                queue.offer(temp.left = new TreeNode<>(left));
            }
            if (!Objects.equals("", right)) {
                queue.offer(temp.right = new TreeNode<>(right));
            }
        }
        return tree;
    }

    public void preOrder(TreeNode<String> tree) {
        if (tree != null) {
            PrintUtil.print(tree.data);
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    public void preOrder1(TreeNode<String> tree) {
        if (tree == null) {
            return;
        }
        Stack<TreeNode<String>> perOrderStack = new Stack<>();
        TreeNode<String> temp = tree;
        perOrderStack.push(temp);
        while (!perOrderStack.empty()) {
            temp = perOrderStack.pop();
            PrintUtil.print(temp.data);
            if (temp.right != null) {
                perOrderStack.push(temp.right);
            }
            if (temp.left != null) {
                perOrderStack.push(temp.left);
            }
        }
    }

    public void inOrder(TreeNode<String> tree) {
        if (tree != null) {
            inOrder(tree.left);
            PrintUtil.print(tree.data);
            inOrder(tree.right);
        }
    }

    /**
     * 支持记录 Node ”操作“次数的StNode，至于什么”操作“，取决于业务，如进行入栈操作等~
     */
    class StNode {
        TreeNode<String> treeNode;
        // 记录入栈次数
        int time;

        StNode(TreeNode<String> treeNode, int time) {
            this.treeNode = treeNode;
            this.time = time;
        }

        StNode(TreeNode<String> treeNode) {
            this(treeNode, 0);
        }

        // 以 StNode 创建一个新的 StNode，对time进行 ++ 操作
        StNode(StNode stNode) {
            this(stNode.treeNode, ++stNode.time);
        }
    }

    public void inOrder1(TreeNode<String> tree) {
        Stack<StNode> inOrderStack = new Stack<>();
        inOrderStack.push(new StNode(tree));
        while (!inOrderStack.empty()) {
            StNode temp = inOrderStack.pop();
            if (temp.treeNode.left != null && temp.time == 0) {
                inOrderStack.push(new StNode(temp));
                inOrderStack.push(new StNode(temp.treeNode.left));
            } else {
                PrintUtil.print(temp.treeNode.data);
                if (temp.treeNode.right != null) {
                    inOrderStack.push(new StNode(temp.treeNode.right));
                }
            }
        }
    }

    public void postOrder(TreeNode<String> tree) {
        if (tree != null) {
            postOrder(tree.left);
            postOrder(tree.right);
            PrintUtil.print(tree.data);
        }
    }

    public void postOrder1(TreeNode<String> tree) {
        Stack<StNode> postOrderStack = new Stack<>();
        postOrderStack.push(new StNode(tree));
        while (!postOrderStack.empty()) {
            StNode temp = postOrderStack.pop();
            if (temp.time == 0) {
                postOrderStack.push(new StNode(temp));
                if (temp.treeNode.left != null) {
                    postOrderStack.push(new StNode(temp.treeNode.left));
                }
            } else if (temp.time == 1) {
                postOrderStack.push(new StNode(temp));
                if (temp.treeNode.right != null) {
                    postOrderStack.push(new StNode(temp.treeNode.right));
                }
            } else if (temp.time == 2) {
                PrintUtil.print(temp.treeNode.data);
            }
        }
    }

    /**
     * 判断给定的两棵树是否是父子关系
     * @param parent    父树
     * @param child     子树
     * @return          true：是父子关系；false：不是父子关系
     */
    public boolean isSubTree(TreeNode<String> parent, TreeNode<String> child) {
        if (child == null) {
            return true;
        }
        if (parent == null) {
            return false;
        }
        boolean result = false;
        // 树根节点开始是否是给定的子树
        if (parent.data.equals(child.data)) {
            result = hasSubTree(parent, child);
        }
        // 如果树根节点开始不是的话，判断左子树是否包含给定的子树
        if (!result) {
            result = isSubTree(parent.left, child);
        }
        // 如果树根节点开始与左子树都不包含给定子树的话，判断右子树是否是给定的子树
        if (!result) {
            result = isSubTree(parent.right, child);
        }
        return result;
    }

    private boolean hasSubTree(TreeNode<String> parent, TreeNode<String> child) {
        if (child == null) {
            return true;
        }
        if (parent == null) {
            return false;
        }
        if (!parent.data.equals(child.data)) {
            return false;
        } else {
            return hasSubTree(parent.left, child.left) && hasSubTree(parent.right, child.right);
        }
    }

    public static void orderTest() {
        Tree tree = new Tree();
        TreeNode<String> treeNode = tree.createTree();
        PrintUtil.print("递归前序遍历：");
        tree.preOrder(treeNode);
        PrintUtil.newLine();
        PrintUtil.print("非递归前序遍历：");
        tree.preOrder1(treeNode);

        PrintUtil.newLine();
        PrintUtil.print("递归中序遍历：");
        tree.inOrder(treeNode);
        PrintUtil.newLine();
        PrintUtil.print("非递归中序遍历：");
        tree.inOrder1(treeNode);

        PrintUtil.newLine();
        PrintUtil.print("递归后续遍历：");
        tree.postOrder(treeNode);
        PrintUtil.newLine();
        PrintUtil.print("非递归后续遍历：");
        tree.postOrder1(treeNode);
    }

    public static void isSubTreeTest() {
        Tree tree = new Tree();
        TreeNode<String> parent = tree.createTree();
        TreeNode<String> child = tree.createTree();
        PrintUtil.println(tree.isSubTree(parent, child));
    }

    public static void main(String[] args) {
        // orderTest();
        isSubTreeTest();
    }


}
