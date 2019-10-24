import ovito
import numpy as np
from PyQt5.QtCore import *
from PyQt5.QtGui import *
import ovito.vis



def render(painter, **args):
	# Draw external circle
	pen = QPen(Qt.SolidLine)
	pen.setWidth(2)
	pen.setColor(QColor(255,255,255))
	painter.setPen(pen)
	externalRadius = 110
	painter.drawEllipse(QPointF(312, 246),externalRadius , externalRadius)

	# Draw internal circle
	pen = QPen(Qt.SolidLine)
	pen.setWidth(2)
	pen.setColor(QColor(255,255,255))
	painter.setPen(pen)
	internalRadius = 50
	painter.drawEllipse(QPointF(312, 246),internalRadius , internalRadius)