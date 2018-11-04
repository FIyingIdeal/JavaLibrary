package structure;

/**
 * @author yanchao
 * @date 2018/11/1 9:30
 */
public class TreeNode<T> {

    T data;
    TreeNode<T> left;
    TreeNode<T> right;

    TreeNode(T data) {
        this.data = data;
    }
}
