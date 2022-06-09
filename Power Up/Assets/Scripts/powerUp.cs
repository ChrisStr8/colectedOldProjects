using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class powerUp : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	void OnCollisionEnter2D(Collision2D other){
		if (other.gameObject.CompareTag ("Wall")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("superSpeed")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("sonic")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("chicken")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("invisible")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("rapidFire")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("shield")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("vampire")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("baby")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("fireshoes")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("goldenDrunk")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("doubleDamage")) {
			Destroy(this.gameObject);
		}
	}

	void OnTriggerEnter2D(Collider2D other){
		if (other.gameObject.CompareTag ("wall")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("superSpeed")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("sonic")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("chicken")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("invisible")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("rapidFire")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("shield")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("vampire")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("baby")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("fireshoes")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("goldenDrunk")) {
			Destroy(this.gameObject);
		} else if (other.gameObject.CompareTag ("doubleDamage")) {
			Destroy(this.gameObject);
		}
	}
}
