using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class ReturnButtonScript : MonoBehaviour
{

    public GameObject menu;
    public GameObject levelSelect;

    // Use this for initialization
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {

    }

    public void startButton()
    {
        menu.SetActive(true);
        levelSelect.SetActive(false);
    }
}
