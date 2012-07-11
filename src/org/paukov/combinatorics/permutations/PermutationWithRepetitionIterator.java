package org.paukov.combinatorics.permutations;

import java.util.ArrayList;
import java.util.List;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.paukov.combinatorics.Iterator;

/**
 * Iterator of permutation with repetitions
 * 
 * @author Dmytro.Paukov
 * @see ICombinatoricsVector
 * @see PermutationWithRepetitionGenerator
 * 
 * @param <T>
 *            Type of elements in the permutation
 */
public class PermutationWithRepetitionIterator<T extends Object> extends
		Iterator<ICombinatoricsVector<T>> {

	/**
	 * Generator
	 */
	protected final PermutationWithRepetitionGenerator<T> _generator;

	/**
	 * Current permutation
	 */
	protected ICombinatoricsVector<T> _currentPermutation = null;

	/**
	 * Index of the current permutation
	 */
	protected long _currentIndex = 0;

	/**
	 * Number of elements in the core vector
	 */
	protected final int _n;

	/**
	 * Number of elements in the generated permutations
	 */
	protected final int _k;

	/**
	 * Internal data
	 */
	private int[] _bitVector = null;

	/**
	 * Constructor
	 * 
	 * @param generator
	 *            Generator
	 */
	public PermutationWithRepetitionIterator(
			PermutationWithRepetitionGenerator<T> generator) {
		_generator = generator;
		_n = generator.getOriginalVector().getSize();
		_k = generator.getPermutationLength();

		List<T> list = new ArrayList<T>(_k);
		T defaultValue = generator.getOriginalVector().getValue(0);
		for (int i = 0; i < _k; i++) {
			list.add(defaultValue);
		}

		_currentPermutation = Factory.createVector(list);

		_bitVector = new int[_k + 2];
		init();
	}

	/**
	 * Initializes iteration process
	 * 
	 * @see org.paukov.combinatorics.Iterator#first()
	 */
	private void init() {
		for (int j = 0; j <= _k; j++) {
			_bitVector[j] = 0;
		}
		_currentIndex = 0;
	}

	/**
	 * Returns current permutation
	 * 
	 * @see org.paukov.combinatorics.Iterator#getCurrentItem()
	 */
	@Override
	public ICombinatoricsVector<T> getCurrentItem() {
		return Factory.createVector(_currentPermutation);
	}

	/**
	 * Returns true if all permutations have been iterated
	 * 
	 * @see org.paukov.combinatorics.Iterator#isDone()
	 */
	@Override
	public boolean isDone() {
		return (_bitVector[_k] == 1);
	}

	/**
	 * Moves to the next permutation
	 * 
	 * @see org.paukov.combinatorics.Iterator#next()
	 */
	@Override
	public ICombinatoricsVector<T> next() {
		_currentIndex++;

		for (int j = _k - 1; j >= 0; j--) {
			int index = _bitVector[j];
			_currentPermutation.setValue(j, _generator.getOriginalVector()
					.getValue(index));
		}

		int i = 0;
		while (_bitVector[i] == _n - 1) {
			if (i < _k + 1)
				_bitVector[i] = 0;
			else {
				_bitVector[_k] = 1;
				return getCurrentItem();
			}
			i++;
		}

		_bitVector[i]++;
		return getCurrentItem();

	}

	/**
	 * Returns the current permutation as a string
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PermutationWithRepetitionIterator=[#" + _currentIndex + ", "
				+ _currentPermutation + "]";
	}
}
