package com.wisnu.tesandroiddev_orsela.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wisnu.tesandroiddev_orsela.R
import com.wisnu.tesandroiddev_orsela.data.db.OrselaDatabase
import com.wisnu.tesandroiddev_orsela.data.repo.OrselaRepo
import com.wisnu.tesandroiddev_orsela.ui.AddLocation
import com.wisnu.tesandroiddev_orsela.ui.adapter.MainAdapter
import com.wisnu.tesandroiddev_orsela.ui.viewmodel.OrselaViewModel
import com.wisnu.tesandroiddev_orsela.ui.viewmodel.OrselaViewModelFactory
import com.wisnu.tesandroiddev_orsela.utils.Coroutines
import com.wisnu.tesandroiddev_orsela.utils.UtilExtensions.myToast
import com.wisnu.tesandroiddev_orsela.utils.UtilExtensions.openActivity
import kotlinx.android.synthetic.main.fragment_all.*


class AllFragment : Fragment() {
    companion object {
        const val ORSELA_DATA = "ORSELA_DATA"
    }

    private lateinit var viewModel: OrselaViewModel
    private lateinit var orselaDatabase: OrselaDatabase
    private lateinit var repository: OrselaRepo
    private lateinit var factory: OrselaViewModelFactory
    private lateinit var mainAdapter: MainAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //@todo bad practice because boilerplate code, but i'll be change this later using DI.
        orselaDatabase = OrselaDatabase(requireContext())
        repository = OrselaRepo(orselaDatabase)
        factory = OrselaViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[OrselaViewModel::class.java]

        mainAdapter = MainAdapter(requireContext()) {
            context?.openActivity(AddLocation::class.java) {
                putParcelable(ORSELA_DATA, it)
            }
        }

        initView()
        observeNotes()


    }
    private fun initView() {


        notesRV.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mainAdapter
        }

        ItemTouchHelper(itemTouchHelperCallback()).attachToRecyclerView(notesRV)
    }

    private fun observeNotes() {
        Coroutines.main {
            viewModel.getAllNotes().observe(viewLifecycleOwner, {
                mainAdapter.submitList(it)
            })
        }
    }

    private fun itemTouchHelperCallback(): ItemTouchHelper.SimpleCallback {
        return object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Coroutines.main {
                    val note = mainAdapter.getNoteAt(viewHolder.adapterPosition)
                    viewModel.deleteNote(note).also {
                        context?.myToast(getString(R.string.success_delete))
                    }
                }
            }
        }
    }

}


