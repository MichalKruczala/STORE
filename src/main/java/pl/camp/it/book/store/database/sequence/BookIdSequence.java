package pl.camp.it.book.store.database.sequence;



public class BookIdSequence implements IBookIdSequence {

    private int id = 0;

    @Override
    public int getId() {
        return ++id;
    }
}
