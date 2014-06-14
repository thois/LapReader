package fi.helsinki.cs.thois.lapreader.model;

/**
 * Base class for inheriting all Models to keep different models in a collection
 */
public abstract class Model {
    
    /**
    * Get data from the model in row format for creating tables
    * @return Array of model data
    */
    public abstract Object[] getRowData();
}
