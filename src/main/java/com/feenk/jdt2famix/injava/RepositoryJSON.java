package com.feenk.jdt2famix.injava;

import ch.akuhn.fame.MetaRepository;
import ch.akuhn.fame.Repository;

public class RepositoryJSON extends Repository {
	
	public RepositoryJSON(MetaRepository metamodel) {
        super(metamodel);
    }

	public void exportJSON(Appendable stream) {
        this.accept(new JSONPrinter(stream));
    }
}