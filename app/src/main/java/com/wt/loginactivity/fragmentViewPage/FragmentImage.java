package com.wt.loginactivity.fragmentViewPage;

import static android.content.Context.BIND_AUTO_CREATE;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.wt.loginactivity.R;
import com.wt.loginactivity.imageDownload.DownloadService;

public class FragmentImage extends Fragment {

    private RecyclerView listImage;
    String[] imageArray = new String[0];

    public FragmentImage(String[] imageArray) {
        Log.d("QX", "FragmentImage >>> imageArray = " + imageArray);
        this.imageArray = imageArray;
        Log.d("QX", "FragmentImage >>> this.imageArray = " + this.imageArray);
    }
    public FragmentImage() {
        //Log.d("QX", "FragmentImage11111 >>> imageArray = " + imageArray);
    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
////
//        Intent intent = new Intent(getContext(), DownloadService.class);
//        context.startService(intent); // 启动服务,保证DownloadService一直在后台运行
//        context.bindService(intent, connection, BIND_AUTO_CREATE); // 绑定服务,让活动和服务通信
//        //申请权限
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }
//   }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getContext(), DownloadService.class);
        //getActivity().startService(intent); // 启动服务,保证DownloadService一直在后台运行
        getActivity().bindService(intent, connection, BIND_AUTO_CREATE); // 绑定服务,让活动和服务通信
        Log.d("bindService","bindService");
        //申请权限
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑活动，否则可能导致内存泄漏
        getActivity().unbindService(connection);
        Log.d("unbindService","unbindService");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image, container, false);
        //mRecyclerView = view.findViewById(R.id.list_image);
        listImage = view.findViewById(R.id.list_image);
        initImage();
        //设置流式瀑布
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        listImage.setLayoutManager(layoutManager);
//        ImageAdapter imageAdapter=new ImageAdapter(imageList);
//         ImageAdapter adapter = new ImageAdapter(imageList, getContext());
//        listImage.setAdapter(adapter);
        return view;
    }

    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
           downloadBinder = (DownloadService.DownloadBinder) service;
//      确保传入ImageAdapter里面的downloadBinder不为空
            if (adapter!= null){
                adapter.setDownloadBinder(downloadBinder);
            }
        }
    };

    private ImageAdapter adapter;

    private void initImage() {
        Log.d("QX", "FragmentImage >>> initImage");
//         imageArray = ImageUtil.imageUrls;
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setAdapter(new ImagerAdapter(getContext()));
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        listImage.setLayoutManager(layoutManager);
        adapter = new ImageAdapter(imageArray, getContext());
        Log.d("QX", "FragmentImage >>> imageArray = " + imageArray);
        listImage.setAdapter(adapter);
//      确保传入ImageAdapter里面的downloadBinder不为空
        if (downloadBinder!=null){
            adapter.setDownloadBinder(downloadBinder);
        }

    }

//
//    public class ImagerAdapter extends RecyclerView.Adapter<ImagerAdapter.ViewHolder> {
//
//        private ImageView imageViewShow;
//        private Bitmap bitmap;
//
//        //handler更新ui
//        private final Handler handler = new Handler(Looper.myLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == 200) {
//                    imageViewShow = new ImageView(getContext());
//                    imageViewShow.setImageBitmap(bitmap);
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
//                    alertDialog.setTitle("你点击的图片为");
//                    alertDialog.setView(imageViewShow);
//                    alertDialog.setPositiveButton("收藏", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            //dialog.cancel();
//                            flag = imageManager.insertImage(urlPath, 0);
//                            if (!flag) {
//                                Toast.makeText(mContext, "收藏失败", Toast.LENGTH_SHORT).show();
//                                return;
//                            } else {
//                                Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
//
////                                List<Image> imageList = DataSupport.findAll(Image.class);
////                                for (Image image : imageList) {
////                                    Log.e("FragmentImage", "path is " + image.getImageSource());
////                                    Log.e("FragmentImage", "userId is " + image.getUserId());
////                                }
//                            }
//                        }
//                    });
//                    alertDialog.setNegativeButton("下载", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // DO SOMETHING HERE
//                            if (downloadBinder == null) {
//                                return;
//                            }
//                            downloadBinder.startDownload(urlPath);
//                        }
//                    });
//
//                    AlertDialog dialog = alertDialog.create();
//                    dialog.show();
//                }
//            }
//        };
//
//        //将值传进来
//        private Context mContext;
//
//        public ImagerAdapter(Context content) {
//            this.mContext = content;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            //获取item_layout的布局
//            View view = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.imageview.getLayoutParams();
//            //设置高
//            params.height = mHeight.get(position);
//            holder.imageview.setLayoutParams(params);
//            //用glide加载网络图片 并放入imageview中
//            Glide.with(getActivity()).load(imageArray[position]).into(holder.imageview);
//            //调用setItemListener 传入三个参数
//            setItemListener(holder, position, imageArray[position]);
//        }
//
//        //设置图片的点击事件
//        private void setItemListener(final ViewHolder holder, final int position, final String url) {
//            //如果holder为空 return；
//            if (holder == null) {
//                return;
//            }
//            //每个图片的点击事件
//            holder.imageview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //获取图片
//                            bitmap = getImageBitmap(url);
//                            urlPath=url;
//                            Message message = new Message();
//                            message.what = 200;
//                            handler.sendMessage(message);
//                        }
//                    }).start();
//
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return imageArray.length;
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            ImageView imageview;
//
//            public ViewHolder(View view) {
//                super(view);
//                imageview = (ImageView) view.findViewById(R.id.image_view);
//            }
//        }
//    }
//
//    //根据url下载图片转换成bitmap格式
//    public Bitmap getImageBitmap(String url) {
//        URL imgUrl = null;
//        Bitmap bitmap = null;
//        try {
//            imgUrl = new URL(url);
//            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//        } catch (MalformedURLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }

}
