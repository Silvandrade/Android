package co.tiagoaguiar.course.instagram.profile.view

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.course.instagram.R

class ProfileFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Disparado depois que a view é criada.
        super.onViewCreated(view, savedInstanceState) // As referências dos elementos fica armazenada no parametro view.

        val rv = view.findViewById<RecyclerView>(R.id.profile_rv)
        rv.layoutManager = GridLayoutManager(requireContext(), 3)
        rv.adapter = PostAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Informa que este fragmento vai gerenciar opções de menu.
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu) // Atribuindo o arquivo de layout do menu dentro do menu.
        super.onCreateOptionsMenu(menu, inflater)
    }

    private class PostAdapter: RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder { // Criação do layout do item.
            val inflater: LayoutInflater = LayoutInflater.from(parent.context)
            val itemView: View = inflater.inflate(R.layout.item_profile_grid, parent, false)

            return PostViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) { // Gerenciamento do item
            val imageView = holder.getImageView()
            imageView.setImageResource(R.drawable.ic_insta_add)
        }

        override fun getItemCount(): Int { // Quantidade de itens.
            return 30
        }

        private class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            fun getImageView(): ImageView {
                return itemView.findViewById<ImageView>(R.id.item_profile_image_grid)
            }
        }
    }
}