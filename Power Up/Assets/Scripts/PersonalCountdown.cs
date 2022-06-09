using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PersonalCountdown : MonoBehaviour
{

    public int countdown = 4;
    public float second = 1.0f;
    public bool start = false;

    // Use this for initialization
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {
        second -= Time.deltaTime;
        if (second < 0 && start == false) {
            second = 1;
            countdown--;
            if (countdown == -1) {
                start = true;
            }
        }
    }
}
