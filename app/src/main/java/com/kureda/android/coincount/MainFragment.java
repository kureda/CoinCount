package com.kureda.android.coincount;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Serg on 12/9/2016.
 *
 * Display total and list of currencies User can add, remove and edit currencies.
 */

public class MainFragment extends Fragment implements Contract.View {

        private Contract.Presenter mPresenter;

        private TasksAdapter mListAdapter;

        private View mNoTasksView;

        private ImageView mNoTaskIcon;

        private TextView mNoTaskMainView;

        private TextView mNoTaskAddView;

        private LinearLayout mTasksView;

        private TextView mFilteringLabelView;

        public TasksFragment() {
            // Requires empty public constructor
        }

        public static TasksFragment newInstance() {
            return new TasksFragment();
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mListAdapter = new TasksAdapter(new ArrayList<Task>(0), mItemListener);
        }

        @Override
        public void onResume() {
            super.onResume();
            mPresenter.start();
        }

        @Override
        public void setPresenter(@NonNull TasksContract.Presenter presenter) {
            mPresenter = checkNotNull(presenter);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            mPresenter.result(requestCode, resultCode);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.tasks_frag, container, false);

            // Set up tasks view
            ListView listView = (ListView) root.findViewById(R.id.tasks_list);
            listView.setAdapter(mListAdapter);
            mFilteringLabelView = (TextView) root.findViewById(R.id.filteringLabel);
            mTasksView = (LinearLayout) root.findViewById(R.id.tasksLL);

            // Set up  no tasks view
            mNoTasksView = root.findViewById(R.id.noTasks);
            mNoTaskIcon = (ImageView) root.findViewById(R.id.noTasksIcon);
            mNoTaskMainView = (TextView) root.findViewById(R.id.noTasksMain);
            mNoTaskAddView = (TextView) root.findViewById(R.id.noTasksAdd);
            mNoTaskAddView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddTask();
                }
            });

            // Set up floating action button
            FloatingActionButton fab =
                    (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);

            fab.setImageResource(R.drawable.ic_add);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.addNewTask();
                }
            });

            // Set up progress indicator
            final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                    (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
            swipeRefreshLayout.setColorSchemeColors(
                    ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                    ContextCompat.getColor(getActivity(), R.color.colorAccent),
                    ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
            );
            // Set the scrolling view in the custom SwipeRefreshLayout.
            swipeRefreshLayout.setScrollUpChild(listView);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mPresenter.loadTasks(false);
                }
            });

            setHasOptionsMenu(true);

            return root;
        }

}
