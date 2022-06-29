package gof.pattern
package gof

import scala.collection.mutable.ArrayBuffer

object Prototype {

  // we can simply use case class
  case class Alert(
                    subElementId: Int,
                    metricMetadataId: Int,
                    metricMetadataHourlyId: Int,
                    metricValue: Int,
                    elementsEffected: ArrayBuffer[Int]
                  )

  // or we can use cloneable interface or copy constructor
  class CloneableAlert(var subElementId: Int,
                       var metricMetadataId: Int,
                       var metricMetadataHourlyId: Int,
                       var metricValue: Int,
                       var elementsEffected: ArrayBuffer[Int]
                      ) extends Cloneable {

    //copy constructor
    def this(copyAlert: CloneableAlert) = {
      this(
        copyAlert.subElementId,
        copyAlert.metricMetadataId,
        copyAlert.metricMetadataHourlyId,
        copyAlert.metricValue,
        copyAlert.elementsEffected.clone())
    }

    //cloneable approach
    override def clone: CloneableAlert = {
      new CloneableAlert(
        this.subElementId,
        this.metricMetadataId,
        this.metricMetadataHourlyId,
        this.metricValue,
        this.elementsEffected.clone()
      )
    }
  }

  def main(args: Array[String]): Unit = {
    val list1: ArrayBuffer[Int] = ArrayBuffer[Int](100, 200, 300, 400)
    val alert1: Alert = Alert(1111, 2222, 3333, 4444, list1);
    val CloneableAlert: Alert = alert1.copy(elementsEffected = alert1.elementsEffected.clone());
    CloneableAlert.elementsEffected += 800;
    println(alert1)
    println(CloneableAlert)

    val list2: ArrayBuffer[Int] = ArrayBuffer[Int](100, 200, 300, 400)
    val alert3: CloneableAlert = new CloneableAlert(1111, 2222, 3333, 4444, list2);
    val alert4: CloneableAlert = new CloneableAlert(alert3);
    alert4.elementsEffected += 800;
    alert3.elementsEffected.foreach(print(_))
    println();
    alert4.elementsEffected.foreach(print(_))
    println();

    val alert5: CloneableAlert = new CloneableAlert(1111, 2222, 3333, 4444, list2);
    val alert6: CloneableAlert = alert5.clone;
    alert6.elementsEffected += 800;
    alert5.elementsEffected.foreach(print(_))
    println();
    alert6.elementsEffected.foreach(print(_))
    println();
  }
}
