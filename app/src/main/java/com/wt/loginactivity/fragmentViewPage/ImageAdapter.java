package com.wt.loginactivity.fragmentViewPage;

import static org.litepal.LitePalApplication.getContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wt.loginactivity.R;
import com.wt.loginactivity.bean.Image;
import com.wt.loginactivity.imageDownload.DownloadService;
import com.wt.loginactivity.manager.ImageManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<Image> mImageList;
    private ImageView imageViewShow;
    private ViewHolder viewHolder;
    private Bitmap bitmap;
    private String urlPath;
    private String[] imageArray;
    private List<Integer> mHeight;

    //
//    public ImageAdapter(List<Image> resultList , Context context) {
//        this.mImageList = resultList;
//        this.mContext = context;
//    }
    private DownloadService.DownloadBinder downloadBinder;

    public void setDownloadBinder(DownloadService.DownloadBinder downloadBinder) {
        this.downloadBinder = downloadBinder;
    }

    public ImageAdapter(String[] imageArray, Context context) {
       // Log.d("QX", "ImageAdapter >>> imageArray = " + imageArray);
        this.imageArray = imageArray;
        this.mContext = context;
       // Log.d("QX", "ImageAdapter >>> this.imageArray = " + this.imageArray);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
//           viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = viewHolder.getAdapterPosition();
//                Image image = mImageList.get(position);
//                //Toast.makeText(v.getContext(), "you clicked this"+image.getImageId(), Toast.LENGTH_SHORT).show();
//            }
//
        //initImage();
//        for (int i = 0; i < imageArray.length; i++) {
//            System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiii"+imageArray[i]);
//        }
//        System.out.println(imageArray.length);
        getRandomHeight();
//        //申请权限
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions((Activity) mContext,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }
        return new ViewHolder(view);
    }

    public void getRandomHeight() {
        //获取所有图片
        mHeight = new ArrayList<>();
        for (int i = 0; i <= imageArray.length; i++) {
            //依次给给图片设置宽高
            mHeight.add((int) (600));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Image image = mImageList.get(position);
//        holder.imageView.setImageResource(image.getId());
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
//                imageViewShow = new ImageView(mContext);
//                imageViewShow.setImageResource(image.getId());
//                alertDialog.setTitle("你点击的图片为");
//                alertDialog.setView(imageViewShow);
//                alertDialog.setPositiveButton("收藏", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //dialog.cancel();
//                    }
//                });
//                alertDialog.setNegativeButton("下载", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        // DO SOMETHING HERE
//
//                    }
//                });
//
//                AlertDialog dialog = alertDialog.create();
//                dialog.show();
//            }
//        });
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.imageView.getLayoutParams();
        //设置高
        params.height = mHeight.get(position);
        holder.imageView.setLayoutParams(params);
        //用glide加载网络图片 并放入imageview中
        Glide.with(mContext).load(imageArray[position]).into(holder.imageView);
        //调用setItemListener 传入三个参数
        setItemListener(holder, position, String.valueOf(imageArray[position]));
    }


    private Context mContext;
    boolean flag;
    private ImageManager imageManager = new ImageManager();
    //handler更新ui
    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                imageViewShow = new ImageView(getContext());
                imageViewShow.setImageBitmap(bitmap);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle("你点击的图片为");
                alertDialog.setView(imageViewShow);
                alertDialog.setPositiveButton("收藏", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.cancel();
                        flag = imageManager.insertImage(urlPath, 0);
                        if (!flag) {
                            Toast.makeText(mContext, "收藏失败", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
//                            List<Image> imageList = DataSupport.findAll(Image.class);
//                            for (Image image : imageList) {
//                                Log.e("FragmentImage", "path is " + image.getImageSource());
//                                Log.e("FragmentImage", "userId is " + image.getUserId());
//                            }
                        }
                    }
                });
                alertDialog.setNegativeButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // DO SOMETHING HERE
                        if (downloadBinder == null) {
                            return;
                        }
                        downloadBinder.startDownload(urlPath);
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        }
    };

    private void setItemListener(ViewHolder holder, int position, final String url) {
        //如果holder为空 return；
        if (holder == null) {
            return;
        }
        //每个图片的点击事件
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //获取图片
                        bitmap = getImageBitmap(url);
                        urlPath = url;
                        Message message = new Message();
                        message.what = 200;
                        handler.sendMessage(message);
                    }
                }).start();

            }
        });
    }

    //根据url下载图片转换成bitmap格式
    private Bitmap getImageBitmap(String url) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

    @Override
    public int getItemCount() {
//       Log.d("QX", "ImageAdapter >>> imageArray = " + imageArray);
        return imageArray.length;
    }

}
