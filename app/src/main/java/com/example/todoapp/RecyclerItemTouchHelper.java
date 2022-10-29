package com.example.todoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Adapter.ToDoAdapter;

public class RecyclerItemTouchHelper<dX> extends ItemTouchHelper.SimpleCallback {
    private ToDoAdapter adapter;

    public RecyclerItemTouchHelper(ToDoAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    public void onSwiped(final RecyclerView.ViewHolder , int direction) {
        RecyclerView.ViewHolder viewHolder = null;
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Görevi sil");
            builder.setMessage("Silmek istediğine emin misin ?");
            builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    adapter.deleteItem(position);
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            adapter.editItem(position);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    Drawable icon;
    ColorDrawable background;

    private RecyclerItemTouchHelper viewHolder;
    View itemView = viewHolder.itemView;
    int backgroundCornerOffset = 20;

    if(dX>0)
    {
        icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_edit_24);
        background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.black));
    } else{
        icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_delete);
        background = new ColorDrawable(Color.RED);
    }
    int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) /2;
    int iconTop = (itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) /2);
    int iconBottom = iconTop + icon.getIntrinsicHeight();

    if(dX>0){
        int iconLeft = itemView.getLeft() + iconMargin;
        int iconRight = itemView.getRight() + iconMargin + icon.getIntrinsicWidth();
        icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);

        Object dX = null;
        background.setBounds(itemView.getLeft(),itemView.getTop(),
                itemView.getLeft()+ ((int)dX) + backgroundCornerOffset, itemView.getBottom());
    }
    else if(dX<0){
        int iconLeft = itemView.getRight() - iconMargin -icon.getIntrinsicWidth();
        int iconRight = itemView.getRight() - iconMargin;
        icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);

        Object dX = null;
        background.setBounds(itemView.getRight() +((int)dX) - backgroundCornerOffset,itemView.getTop(),
                itemView.getRight(), itemView.getBottom());
    }
    else{
        background.setBounds(0,0,0,0);
        Canvas c = null;
        background.draw(c);
        icon.draw(c);
    }
    }
}

