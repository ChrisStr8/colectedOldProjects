struct semaphore 
  {
    unsigned value;             /* Current value. */
    struct list waiters;        /* List of waiting threads. */
  };
  
void p(struct semaphore *sema){
	ASSERT (sema != NULL);
	
	bool key = true;
	
	while (sema->value == 0) 
    {
		while(key){ 
			swap(lock, key);
			list_push_back (&sema->waiters, &thread_current ()->elem);
			thread_block ();
			key = false;
		}
    }
	sema->value--;
}

void v(struct semaphore *sema){
	ASSERT (sema != NULL);
	
	bool key = true;

	while(key){ 
		swap(lock, key);
		if (!list_empty (&sema->waiters)) 
			thread_unblock (list_entry (list_pop_front (&sema->waiters), struct thread, elem));
		sema->value++;
		lock = false;
	}
}