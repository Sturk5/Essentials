package org.shouthost.essentials.utils;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import org.shouthost.essentials.json.books.Books;
import org.shouthost.essentials.json.books.Page;

import java.util.Iterator;

//TODO: Review
public class Book {
    public static ItemStack CreateBook(Books b) {
        ItemStack book = new ItemStack(Items.written_book);
        if (book.stackTagCompound == null)
            book.stackTagCompound = new NBTTagCompound();

        NBTTagCompound bookTag = book.stackTagCompound;

        bookTag.setString("title", b.getTitle());
        bookTag.setString("author", b.getAuthor());

        NBTTagList bookPages = new NBTTagList();
        bookTag.setTag("pages", bookPages);

        Iterator<Page> page = b.getPage().iterator();

        while (page.hasNext()) {
            Iterator<String> it = page.next().getContent().iterator();
            while (it.hasNext()) {
                bookPages.appendTag(new NBTTagString(it.next()));
            }
        }

        return book;
    }
}
