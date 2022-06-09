using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TeamUtility.IO;

public class HealthTrackerScript : MonoBehaviour {

    public GameObject playerKnight;
    float maxWidth;

	// Use this for initialization
	void Start () {
        maxWidth = transform.localScale.x;
	}
	
	// Update is called once per frame
	void Update () {
        if (playerKnight == null) {
            transform.localScale = new Vector3(0, 0.16435f, 0);
        } else {
            transform.localScale = new Vector3(((float)playerKnight.GetComponent<CharacterControler>().getHealth() / 100) * maxWidth, 0.16435f, 0);
        }
		
	}
}
