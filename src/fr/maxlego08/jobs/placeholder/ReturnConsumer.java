package fr.maxlego08.jobs.placeholder;
@FunctionalInterface
public interface ReturnConsumer<T, G> {

	G accept(T t);
	
}