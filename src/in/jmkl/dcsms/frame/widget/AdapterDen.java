package in.jmkl.dcsms.frame.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterDen extends BaseAdapter {
    
    private Activity activity;
    private List<String> data;
    private static LayoutInflater inflater=null;
    
    
    public AdapterDen(Activity a, List<String> listtheme) {
        activity = a;
        data=listtheme;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
        
        GetBitmap g = new GetBitmap();
		g. getBtimapFromZip(data.get(position).toString(),"preview.png");
		Bitmap bprev = g.getBitmapResult();
		Keterangan k = new Keterangan();
		k.getKeterangan(data.get(position).toString(), "desc.xml");

        TextView judul = (TextView)vi.findViewById(R.id.judul);
        TextView autor = (TextView)vi.findViewById(R.id.autor);
        TextView versi = (TextView)vi.findViewById(R.id.versi);
        ImageView preview=(ImageView)vi.findViewById(R.id.preview);
        
        judul.setText(k.getTitle());
        autor.setText(k.getAuthor());
        versi.setText(k.getVersion());
        preview.setImageBitmap(bprev);
      
      //  song = data.get(position);
        
        // Setting all values in listview
       // title.setText(song.get(CustomizedListView.KEY_TITLE));
       // artist.setText(song.get(CustomizedListView.KEY_ARTIST));
       // duration.setText(song.get(CustomizedListView.KEY_DURATION));
      //  imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}