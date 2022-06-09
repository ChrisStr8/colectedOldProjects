using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Countdown : MonoBehaviour {

    public int countdown = 4;
    public float second = 1.0f;
    public bool start = false;

    public GameObject three;
    public GameObject two;
    public GameObject one;
    public GameObject startWord;

    // Use this for initialization
    void Start () {
		
	}

    // Update is called once per frame
    void Update()
    {
        second -= Time.deltaTime;
        if (second < 0) {
            second = 1;
            countdown--;
            if (countdown == 3) {
                three.SetActive(true);
            } else if (countdown == 2) {
                three.SetActive(false);
                two.SetActive(true);
            } else if (countdown == 1) {
                two.SetActive(false);
                one.SetActive(true);
            } else if (countdown == 0) {
                one.SetActive(false);
                startWord.SetActive(true);
            } else if (countdown == -1) {
                startWord.SetActive(false);
                start = true;
            }
        }
    }
}
