package com.nuaa233.sqlite.Disscussion.newItem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nuaa233.sqlite.R;

import java.util.List;

public class itemAdapter extends ArrayAdapter {
    private int resource;
    private List<ItemModel> listItems;
    private Context context;
    private String CurrentUser;

    // 构造方法，传入布局、context和数据源list
    public itemAdapter(Context context, int resource, List<ItemModel> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.listItems = objects;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ItemModel getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public int getCount() {
        if (null == listItems) {
            return 0;
        }
        return listItems.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewgroup) {
        final ItemModel item = listItems.get(position);
        final View view;
        final ViewHolder viewholder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, null);
            viewholder = new ViewHolder();
            viewholder.imgHead = (ImageView) view.findViewById(R.id.imgHead);
            viewholder.tvName = (TextView) view.findViewById(R.id.tvName);
            viewholder.tvDate = (TextView) view.findViewById(R.id.tvDate);
            viewholder.tvContent = (TextView) view.findViewById(R.id.tvContent);
            //viewholder.ivPhone = (ImageView) view.findViewById(R.id.ivPhone);
            //viewholder.tvPhonemodel = (TextView) view.findViewById(R.id.tvPhonemodel);
            //viewholder.ivComment = (ImageView) view.findViewById(R.id.ivComment);
            //viewholder.tvComment = (TextView) view.findViewById(R.id.tvComment);
            //viewholder.tvPhrase = (TextView) view.findViewById(R.id.tvPhrase);
            //viewholder.ivPhrase = (ImageView) view.findViewById(R.id.ivPhrase);
            //viewholder.ivAgreeShow = (ImageView) view.findViewById(R.id.ivAgreeShow);
            //viewholder.tvAgreeShow = (TextView) view.findViewById(R.id.tvAgreeShow);
            //viewholder.commentList = (ListView) view.findViewById(R.id.commentList);
            // viewholder.btnComment = (Button)
            // view.findViewById(R.id.btnComment);
            //viewholder.etComment = (EditText) view.findViewById(R.id.etComment);
            //viewholder.btnSendComment = (Button) view.findViewById(R.id.btnSendComment);
            //viewholder.ivCancel = (ImageView) view.findViewById(R.id.ivCancel);
            //viewholder.testcommentlines = (TextView) view.findViewById(R.id.testcommentlines);
            view.setTag(viewholder);
        } else {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
        // notifyDataSetChanged();
        // TODO 设置头像，待完善
        //viewholder.imgHead.setImageResource(item.getHeadImg());
        // viewholder.imgHead.setImageResource(R.drawable.head);
        viewholder.tvName.setText(item.getUserName());
        viewholder.tvDate.setText(item.getShuoDate());
        viewholder.tvContent.setText(item.getShuoContent());
        //viewholder.ivPhone.setImageResource(R.mipmap.status_phone);
        //viewholder.tvPhonemodel.setText(item.getShuoPhoneModel());
        //viewholder.ivComment.setImageResource(R.mipmap.comment);
//        if (item.getShuoCommentNum() > 0) {
//            viewholder.tvComment.setText(item.getShuoCommentNum() + "评论");
//        } else {
//            viewholder.tvComment.setText("评论");
//        }
//        if (item.getIsPhrase()) {
//            viewholder.ivAgreeShow.setVisibility(View.VISIBLE);
//            viewholder.tvAgreeShow.setVisibility(View.VISIBLE);
//            viewholder.ivPhrase.setImageResource(R.mipmap.phrase);
//            viewholder.tvPhrase.setText("取消赞");
//            if (item.getShuoPhraseNum() > 0) {
//                // 可以通过Html.fromHtml()将字体设置颜色
//                viewholder.tvAgreeShow.setText(Html
//                        .fromHtml("<font color='#1C86EE'>我</font>与"
//                                + item.getShuoPhraseNum() + "人觉得很赞"));
//            } else {
//                viewholder.tvAgreeShow.setText(Html
//                        .fromHtml("<font color='#1C86EE'>我</font>觉得很赞"));
//            }
//
//        } else {
//            viewholder.ivPhrase.setImageResource(R.mipmap.unphrase);
//            viewholder.tvPhrase.setText("赞");
//            if (item.getShuoPhraseNum() > 0) {
//                viewholder.ivAgreeShow.setVisibility(View.VISIBLE);
//                viewholder.tvAgreeShow.setVisibility(View.VISIBLE);
//                viewholder.tvAgreeShow.setText(item.getShuoPhraseNum()
//                        + "人觉得很赞");
//            } else {
//                viewholder.ivAgreeShow.setVisibility(View.GONE);
//                viewholder.tvAgreeShow.setVisibility(View.GONE);
//            }
//        }
//
//
//
//        viewholder.ivPhrase
//                .setOnClickListener(new ListViewButtonOnClickListener(position));
//        viewholder.ivComment
//                .setOnClickListener(new ListViewButtonOnClickListener(position));
//        viewholder.ivComment.setFocusable(false);
//        viewholder.tvComment
//                .setOnClickListener(new ListViewButtonOnClickListener(position));
        // viewholder.btnComment
        // .setOnClickListener(new ListViewButtonOnClickListener(position));
//		viewholder.btnComment.setFocusable(false);
        // notifyDataSetChanged();

        viewholder.imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("666", "img push");
                ItemModel item = listItems.get(position);
                Intent intent = new Intent(context, UserInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("post_id", item.getShuoId());
                bundle.putString("usr_id", item.getUsrId());
                Log.d("666", "item post_id is " + item.getShuoId());
                Log.d("666", "item usr_id is " + item.getUsrId());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
        return view;
    }

//	/**
//	 * 添加一条记录
//	 *
//	 * @param model
//	 */
//	public void addModel(ItemModel model) {
//		listItems.add(model);
//	}
//
//	/**
//	 * 添加一条记录
//	 *
//	 * @param model
//	 * @param insertHead
//	 *            true:插入在头部
//	 */
//	public void addModel(ItemModel model, boolean insertHead) {
//		if (insertHead) {
//			listItems.add(0, model);
//		} else {
//			listItems.add(model);
//		}
//	}
//
//	/**
//	 * 获取一条记录
//	 *
//	 * @param i
//	 * @return
//	 */
//	public ItemModel getModel(int i) {
//		if (i < 0 || i > listItems.size() - 1) {
//			return null;
//		}
//		return listItems.get(i);
//	}

    /**
     * 清除所有数据
     */
    public void clear() {
        if (listItems != null) {
            listItems.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 重新添加数据
     *
     * @author ubuntu
     *
     */
    public void refresh(List<ItemModel> list) {
        if (listItems != null) {
            listItems = list;
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        ImageView imgHead;
        TextView tvName;
        TextView tvDate;
        TextView tvContent;
        ImageView ivPhone;
        TextView tvPhonemodel;
        ImageView ivComment;
        TextView tvComment;
        TextView tvPhrase;
        ImageView ivPhrase;
        ImageView ivAgreeShow;
        TextView tvAgreeShow;
        TextView tvComments;
        Button btnComment;
        ImageView ivCancel;
        Button btnSendComment;
        EditText etComment;
        ListView commentList;
        TextView testcommentlines;
    }

    class ListViewButtonOnClickListener implements View.OnClickListener {
        private int position;// 记录ListView中Button所在的Item的位置
        private ViewGroup viewgroup;

        public ListViewButtonOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.ivPhrase:
//                    Log.i("TAG", "说说" + position + "点赞按钮被点击了");
//                    Activity activity4 = (Activity) context;
//                    CurrentUser = ((TextView) activity4
//                            .findViewById(R.id.tv_userName)).getText().toString();
//                    final ChangeShuoPhraseNum phrase = new ChangeShuoPhraseNum();
//                    ImageView ivAgree = (ImageView) v;
//                    final ItemModel item = listItems.get(position);
//                    // Toast.makeText(getContext(), "点赞按钮被点击了" + item.getIsPhrase(),
//                    // Toast.LENGTH_SHORT).show();
//                    if (item.getIsPhrase()) {
//                        // item.setShuoPhraseNum(item.getShuoPhraseNum() - 1);
//                        ivAgree.setImageResource(R.drawable.unphrase);
//                        new Thread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                phrase.subShuoPhraseNum(item.getShuoId());
//                            }
//                        }).start();
//                        new Thread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                phrase.delShuoPhrase(item.getShuoId(), CurrentUser);
//                            }
//                        }).start();
//                    } else {
//                        // item.setShuoPhraseNum(item.getShuoPhraseNum() + 1);
//                        ivAgree.setImageResource(R.drawable.phrase);
//                        final Phrase p = new Phrase();
//                        p.setPhraseName(CurrentUser);
//                        p.setShuoId(item.getShuoId());
//                        new Thread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                phrase.addShuoPhraseNum(item.getShuoId());
//                            }
//                        }).start();
//                        new Thread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                phrase.addShuoPhrase(p);
//                            }
//                        }).start();
//                    }
//                    item.setIsPhrase(!item.getIsPhrase());
//                    // 刷新布局
//                    notifyDataSetChanged();
//                    break;

//                case R.id.ivComment:
//                    // case R.id.btnComment:
//                case R.id.tvComment:
//                    Log.i("TAG", "说说" + position + "评论被点击了");
//                    // 弹出输入法软件盘
//                    InputMethodManager imm = (InputMethodManager) v.getContext()
//                            .getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
//                    ItemModel item1 = listItems.get(position);
//                    String nikename = item1.getUserName();
//                    Log.i("TAG", "nikename=" + nikename);
//                    // 通过context可以获取到当前activity，获取到当前activity是MainActivity，再通过MainActivity获取到布局
//                    Activity activity1 = (Activity) context;
//                    Log.i("TAG", "activity=" + activity1.toString());
//                    // 将下方隐藏的评论框和按钮显示出来
//                    activity1.findViewById(R.id.etComment).setVisibility(
//                            View.VISIBLE);
//                    activity1.findViewById(R.id.btnSendComment).setVisibility(
//                            View.VISIBLE);
//                    activity1.findViewById(R.id.ivCancel).setVisibility(
//                            View.VISIBLE);
//                    Log.i("TAG", "将评论框显示出来"
//                            + activity1.findViewById(R.id.ivCancel).getVisibility()
//                            + " "
//                            + activity1.findViewById(R.id.btnSendComment)
//                            .getVisibility()
//                            + " "
//                            + activity1.findViewById(R.id.etComment)
//                            .getVisibility());
//                    ((EditText) activity1.findViewById(R.id.etComment)).setHint("@"
//                            + nikename);
//                    activity1.findViewById(R.id.etComment).requestFocus();
//                    activity1.findViewById(R.id.etComment).setFocusable(true);
//                    activity1.findViewById(R.id.btnSendComment).setOnClickListener(
//                            new ListViewButtonOnClickListener(position));
//                    activity1.findViewById(R.id.ivCancel).setOnClickListener(
//                            new ListViewButtonOnClickListener(position));
////				notifyDataSetChanged();
//                    break;
//
//                case R.id.ivCancel:
//                    Activity activity3 = (Activity) context;
//                    ((EditText) activity3.findViewById(R.id.etComment)).setText("");
//                    activity3.findViewById(R.id.etComment).setVisibility(View.GONE);
//                    activity3.findViewById(R.id.btnSendComment).setVisibility(
//                            View.GONE);
//                    activity3.findViewById(R.id.ivCancel).setVisibility(View.GONE);
//                    // 隐藏输入法软键盘
//                    InputMethodManager imm3 = (InputMethodManager) v.getContext()
//                            .getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm3.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
////				notifyDataSetChanged();
//                    break;
//                default:
//                    break;
            }
        }

    }

}
