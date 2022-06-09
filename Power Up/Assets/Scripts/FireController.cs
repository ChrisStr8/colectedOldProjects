using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FireController : MonoBehaviour {

    private const float fireTime = 1f;

	// Use this for initialization
	void Start () {
		Invoke("RemoveFire", fireTime);
        GetComponentInChildren<FireParticleEffect>().fireEnabled = true;
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    void RemoveFire() {
        Destroy(this.gameObject);
    }
}
