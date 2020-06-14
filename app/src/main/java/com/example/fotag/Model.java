// Skeleton was based off sample code in 8.Android/6.MVC2/app/src/main/java/com/example/cs349/mvc2/Model.java

package com.example.fotag;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

class Model extends Observable
{
    // Create static instance of this mModel
    private static Model ourInstance;
    static Model getInstance()
    {
        if (ourInstance == null) {
            ourInstance = new Model();
        }
        return ourInstance;
    }

    // Private Variables
    private float filter;
    List<GridItem> grid_items;                  // List of all added GridItems
    List<GridItem> grid_items_filtered;         // List of displayed GridItems according to filter
    GridItem selectedGridItem;                  // GridItem to be displayed in full-screen image activity

    Model() {
        filter = 0;
        grid_items = new ArrayList<>();
        grid_items_filtered = new ArrayList<>();
    }

    // Setters and getters
    public void setFilter(float filter) {
        this.filter = filter;
        filterGridItems();
    }

    public void setSelectedGridItem(GridItem gridItem) {
        selectedGridItem = gridItem;
    }

    public List<GridItem> getGridItems() {
        return grid_items;
    }

    public List<GridItem> getFilteredGridItems() {
        return grid_items_filtered;
    }

    public GridItem getSelectedGridItem() {
        return selectedGridItem;
    }

    public void insertGridItem(GridItem gridItem, int index) {
        grid_items.add(index, gridItem);
        filterGridItems();
        setChanged();
        notifyObservers();
    }

    // Deletes GridItem corresponding to imageView
    public void deleteGridItem(ImageView imageView) {
        for (int i = 0; i < grid_items.size(); i ++) {
            if (grid_items.get(i).getImageView() == imageView) {
                grid_items.remove(i);
                break;
            }
        }
        filterGridItems();
        setChanged();
        notifyObservers();
    }

    public void clearGridItems() {
        grid_items.clear();
        filterGridItems();
        setChanged();
        notifyObservers();
    }

    void filterGridItems() {
        grid_items_filtered.clear();
        for (GridItem gridItem : grid_items) {
            if (gridItem.getRating() >= filter) {
                grid_items_filtered.add(gridItem);
            }
        }
        setChanged();
        notifyObservers();
    }

    public void setRatingOnSelectedGridItem(float rating) {
        selectedGridItem.setRating(rating);
        filterGridItems();
        setChanged();
        notifyObservers();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Observable Methods
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Helper method to make it easier to initialize all observers
     */
    public void initObservers()
    {
        setChanged();
        notifyObservers();
    }

    /**
     * Deletes an observer from the set of observers of this object.
     * Passing <CODE>null</CODE> to this method will have no effect.
     *
     * @param o the observer to be deleted.
     */
    @Override
    public synchronized void deleteObserver(Observer o)
    {
        super.deleteObserver(o);
    }

    /**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the set.
     * The order in which notifications will be delivered to multiple
     * observers is not specified. See the class comment.
     *
     * @param o an observer to be added.
     * @throws NullPointerException if the parameter o is null.
     */
    @Override
    public synchronized void addObserver(Observer o)
    {
        super.addObserver(o);
    }

    /**
     * Clears the observer list so that this object no longer has any observers.
     */
    @Override
    public synchronized void deleteObservers()
    {
        super.deleteObservers();
    }

    /**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers
     * and then call the <code>clearChanged</code> method to
     * indicate that this object has no longer changed.
     * <p>
     * Each observer has its <code>update</code> method called with two
     * arguments: this observable object and <code>null</code>. In other
     * words, this method is equivalent to:
     * <blockquote><tt>
     * notifyObservers(null)</tt></blockquote>
     *
     * @see Observable#clearChanged()
     * @see Observable#hasChanged()
     * @see Observer#update(Observable, Object)
     */
    @Override
    public void notifyObservers()
    {
        super.notifyObservers();
    }
}
