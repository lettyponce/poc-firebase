package com.example.lfuzzatti.poc1.controller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SwipeController extends ItemTouchHelper.Callback {


    private boolean swipeBack = false;

    //estado do botão que sera exibido no recyclerview
    enum ButtonsState {
        GONE,
        LEFT_VISIBLE,
        RIGHT_VISIBLE
    }

    private ButtonsState buttonShowedState = ButtonsState.GONE;

    //Tamanho do botão que sera exibido
    private static final float buttonWidth = 300;

    private RectF buttonInstance = null;

    private RecyclerView.ViewHolder currentItemViewHolder = null;

    private SwipeControllerActions swipeControllerActions;

    public SwipeController() {
    }

    public SwipeController(SwipeControllerActions buttonsActions) {
        this.swipeControllerActions = buttonsActions;
    }

    /**
     * Obtem qual tipo de ação esta ocorrendo no recyclerview e como deve lidar
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    /**
     * Metodo que executa a ação dada
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * Metodo que executa a ação dada
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = buttonShowedState != ButtonsState.GONE;
            return 0;
        }

        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            if (buttonShowedState != ButtonsState.GONE) {

                if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
                    dX = Math.max(dX, buttonWidth);
                }

                if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
                    dX = Math.min(dX, -buttonWidth);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            } else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if (buttonShowedState == ButtonsState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        currentItemViewHolder = viewHolder;
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder) {

        float buttonWidthPadding = buttonWidth - 20;
        float corners = 16;

        View itemView = viewHolder.itemView;
        Paint paint = new Paint();

        RectF leftButton = new RectF(itemView.getLeft(),
                                     itemView.getTop(),
                               itemView.getLeft() + buttonWidthPadding,
                                     itemView.getBottom());

        paint.setColor(Color.BLUE);

        c.drawRoundRect(leftButton,
                        corners,
                        corners,
                        paint);

        drawText("EDIT", c, leftButton, paint);

        RectF rightButton = new RectF(itemView.getRight() - buttonWidthPadding,
                                      itemView.getTop(),
                                itemView.getRight(),
                                      itemView.getBottom());

        paint.setColor(Color.RED);

        c.drawRoundRect(rightButton,
                        corners,
                        corners,
                        paint);

        drawText("DELETE", c, rightButton, paint);

        buttonInstance = null;

        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
            buttonInstance = leftButton;
        } else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
            buttonInstance = rightButton;
        }

    }

    private void drawText(String text, Canvas c, RectF button, Paint paint) {

        float textSize = 60;
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);

        float textWidth = paint.measureText(text);
        c.drawText(text, button.centerX() - (textWidth/2), button.centerY() + (textSize/2), paint);

    }


    /**
     * Método responsavel em escutar a açao de touch no recyclerview
     *
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    private void setTouchListener(final Canvas c,
                                  final RecyclerView recyclerView,
                                  final RecyclerView.ViewHolder viewHolder,
                                  final float dX,
                                  final float dY,
                                  final int actionState,
                                  final boolean isCurrentlyActive) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                swipeBack = motionEvent.getAction() == MotionEvent.ACTION_CANCEL ||
                        motionEvent.getAction() == MotionEvent.ACTION_UP;

                if (swipeBack) {

                    if (dX < -buttonWidth) {
                        buttonShowedState = ButtonsState.RIGHT_VISIBLE;
                    } else if (dX > buttonWidth) {
                        buttonShowedState = ButtonsState.LEFT_VISIBLE;
                    }

                    if (buttonShowedState != ButtonsState.GONE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }

                return false;
            }
        });
    }

    private void setTouchDownListener(final Canvas c,
                                      final RecyclerView recyclerView,
                                      final RecyclerView.ViewHolder viewHolder,
                                      final float dX,
                                      final float dY,
                                      final int actionState,
                                      final boolean isCurrentlyActive) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }

                return false;
            }
        });
    }

    private void setTouchUpListener(final Canvas c,
                                    final RecyclerView recyclerView,
                                    final RecyclerView.ViewHolder viewHolder,
                                    final float dX,
                                    final float dY,
                                    final int actionState,
                                    final boolean isCurrentlyActive) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    SwipeController.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);

                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return false;
                        }
                    });

                    setItemsClickable(recyclerView, true);
                    swipeBack = false;

                    if (buttonInstance != null && swipeControllerActions != null &&
                            buttonInstance.contains(motionEvent.getX(), motionEvent.getY())){

                        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
                            swipeControllerActions.onLeftClicked(viewHolder.getAdapterPosition());
                        } else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
                            swipeControllerActions.onRightClicked(viewHolder.getAdapterPosition());
                        }
                    }

                    buttonShowedState = ButtonsState.GONE;
                }


                return false;
            }
        });
    }


    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    public void onDraw(Canvas c) {
        if (currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder);
        }
    }
}
