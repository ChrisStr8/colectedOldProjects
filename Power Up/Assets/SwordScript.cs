using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SwordScript : MonoBehaviour {

    // Use this for initialization
    void Start() {

    }

    // Update is called once per frame
    void Update() {

    }

    void OnTriggerEnter2D(Collider2D other) {
        if (!GetComponentInParent<CharacterControler>().swordSwipe) {
            if (other.gameObject.CompareTag("Arrow")) {
                GameObject arrow = other.gameObject;
                Destroy(arrow);
            }
        }
    }
}
