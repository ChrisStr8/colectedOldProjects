using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DeathAnimation : MonoBehaviour {

    public Animation animation;

	// Use this for initialization
	void Start () {
        animation["animation"].wrapMode = WrapMode.Once;
        animation.Play("animation");
    }
	
	// Update is called once per frame
	void Update () {
		
	}
}
