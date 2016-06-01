package cn.asiontang.nleveltreelistview;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AsionTang
 * @since 2016年6月1日 18:38:43
 */
@SuppressWarnings("unused")
public class NLevelTreeNode
{
    private final List<NLevelTreeNode> mChilds = new ArrayList<>();
    private CharSequence mId;
    private int mLevel = 0;
    private CharSequence mName;
    private NLevelTreeNode mParentNode;

    public NLevelTreeNode()
    {
    }

    public NLevelTreeNode(final NLevelTreeNode parentNode, final int level, final CharSequence id, final CharSequence name)
    {
        this.setParentNode(parentNode);
        this.setLevel(level);
        this.setID(id);
        this.setName(name);
    }

    public NLevelTreeNode(final int level, final CharSequence id, final CharSequence name)
    {
        this(null, level, id, name);
    }

    public NLevelTreeNode(final CharSequence id, final CharSequence name)
    {
        this(null, 0, id, name);
    }

    public NLevelTreeNode(final CharSequence name)
    {
        this(null, 0, name, name);
    }

    /**
     * 为此Node添加一个子节点
     */
    public NLevelTreeNode addChild(final NLevelTreeNode child)
    {
        if (!this.mChilds.contains(child))
        {
            this.mChilds.add(child);
            child.setParentNode(this);
        }
        return this;
    }

    /**
     * 设置此Node所属的所有子节点
     */
    public NLevelTreeNode addChilds(final List<NLevelTreeNode> childs)
    {
        for (final NLevelTreeNode child : childs)
            this.addChild(child);
        return this;
    }

    /**
     * 获取此Node指定位置的子节点
     */
    public NLevelTreeNode getChild(final int index)
    {
        return this.mChilds.get(index);
    }

    /**
     * 获取此Node所属的所有子节点
     */
    public List<NLevelTreeNode> getChilds()
    {
        return this.mChilds;
    }

    /**
     * 获取当前Node 唯一标识符（当此Node被点击时，可供区分被点击的是谁）
     */
    public CharSequence getID()
    {
        return this.mId;
    }

    /**
     * 设置当前Node 唯一标识符（当此Node被点击时，可供区分被点击的是谁）
     */
    public NLevelTreeNode setID(final CharSequence id)
    {
        this.mId = id;
        return this;
    }

    /**
     * 获取当前Node所属哪个层级；一般从0级（根节点）开始递增。
     */
    public int getLevel()
    {
        return this.mLevel;
    }

    /**
     * 设置当前Node所在的层级；一般从0级（根节点）开始递增。
     */
    public NLevelTreeNode setLevel(final int level)
    {
        this.mLevel = level;

        //必须立即更新子节点的级别，否则就乱套了。
        for (final NLevelTreeNode child : this.mChilds)
            child.setLevel(level + 1);
        return this;
    }

    /**
     * 获取当前Node 名字
     */
    public CharSequence getName()
    {
        return this.mName;
    }

    /**
     * 设置当前Node 名字
     */
    public NLevelTreeNode setName(final CharSequence name)
    {
        this.mName = name;
        return this;
    }

    /**
     * 获取 此Note 的父节点
     */
    public NLevelTreeNode getParentNode()
    {
        return this.mParentNode;
    }

    /**
     * 设置 此Note 的父节点
     */
    public NLevelTreeNode setParentNode(final NLevelTreeNode parentNode)
    {
        this.mParentNode = parentNode;
        if (parentNode != null)
        {
            parentNode.addChild(this);
            this.setLevel(parentNode.getLevel() + 1);
        }
        return this;
    }
}